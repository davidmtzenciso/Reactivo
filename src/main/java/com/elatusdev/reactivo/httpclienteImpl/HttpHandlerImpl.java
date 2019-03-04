/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.httpclienteImpl;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.elatusdev.reactivo.stream.HttpPUDResponseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.elatusdev.reactivo.stream.HttpGETResponseListener;
import com.elatusdev.reactivo.httpcliente.HttpHandler;

/**
 *
 * @author root
 */
public class HttpHandlerImpl extends Thread implements HttpHandler{
    
    public HttpHandlerImpl(){
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        getListeners = new LinkedList<>();
        pudListeners = new LinkedList<>();
        REQUEST_SUCCESSFUL = "Información guardada!";
        REQUEST_ERROR = new StringBuilder()
                .append("Error de comunicación con el servidor")
                .append("\nChecar Logs por mas información").toString();
    }
    
    @Override
    public void onGETRequestEvent(HttpEvent<HttpRequestGET> evt){
        this.enviarPeticionGET(evt.getRequest());
    }
    
    @Override
    public void onPUDRequestEvent(HttpEvent<HttpRequestPUD> evt){
        this.sendPOST(evt.getRequest());
    }

    private void waitForPUDResponse(HttpRequestPUD request){
        while(request.getResponse().getStatusLine() == null){}
        if(request.getResponse().getStatusLine().getStatusCode() == 200){
            logSuccess(request.getElement().getClass().getSimpleName());
            sendSuccessMessage(request);
        }
        else{
            logError(request.getResponse());
            sendErrorMessage(request);
        }
    }
    
    private void waitForGETResponse(HttpRequestGET request){
        for(int i=10;request.getResponse().getStatusLine() == null; i+=10){
            if(request.hasProgressUpdater())
                request.getProgressUpdater().accept(i);
        }
        if(request.getResponse().getStatusLine().getStatusCode() == 200){
            logSuccess(request.getType().getSimpleName());
            sendSuccessMessage(request);
        }
        else{
            sendErrorMessage(request);
            logError(request.getResponse());
        }
    }
    
    private void logSuccess(String msg){
        LOG.log(Level.INFO, "successful petition sended {0}", msg);
    }
    
    private void logError(HttpResponse response){
        StringBuilder log;
        BufferedReader reader;
        
        try{
            reader = new BufferedReader(new InputStreamReader(
                                    response.getEntity().getContent()));
            log = new StringBuilder();
            reader.lines().forEach(l->log.append(l).append("\n"));
        LOG.log(Level.SEVERE, "{0} {1} Error: {2}", new Object[]{
            LocalDate.now(),LocalTime.now(), log});
        }catch(IOException e){
            LOG.log(Level.SEVERE,"{0} {1} Exception:{2}, msj:{3}",
                     new Object[]{LocalDate.now(),LocalTime.now(),e,e.getMessage()});
        }
    }
    
    private void sendSuccessMessage(HttpRequestPUD petition){                        
        pudListeners.forEach(l->
                l.onPUDResponse(new HttpEvent<>(this, petition)));
    }
    
    private void sendSuccessMessage(HttpRequestGET petition){  
        getListeners.forEach(l->l.onGETResponse(new HttpEvent<>(this, petition)));
    }
    
    private void sendErrorMessage(HttpRequestPUD request){
        pudListeners.forEach(l->l.onPUDError(request.getElement().getClass(),
                REQUEST_ERROR));
    }
    
    private void sendErrorMessage(HttpRequestGET petition) {
        getListeners.forEach(l->l.onGETError(petition.getType(), REQUEST_ERROR));
    }
    
    @Override
    public void setGETEventListener(HttpGETResponseListener listener){
        this.getListeners.add(listener);
    }
    
    @Override
    public void setPUDEventListener(HttpPUDResponseListener listener){
        this.pudListeners.add(listener);
    }
   
    private void sendPOST(HttpRequestPUD request) {
        HttpClient client;
        HttpPost post;
        String json;
        
        try{
            client = HttpClientBuilder.create().build();
            json = mapper.writeValueAsString(request.getElement());
            post = new HttpPost(request.getUrl());
            post.setEntity(new StringEntity(json, "UTF-8"));
            post.setHeader("Content-type", "application/json;charset=UTF-8");
            request.setReponse(client.execute(post));
            new Thread(()->waitForPUDResponse(request)).start();
        }catch(IOException e){
            LOG.log(Level.SEVERE, "Error preparing POST, cause: {0}",e);
            sendErrorMessage(request);
        }
    }
    
    private void enviarPeticionGET(HttpRequestGET request) {
        HttpClient cliente; 
        HttpGet method;
        
        try{
            cliente = HttpClientBuilder.create().build();
            method = new HttpGet(request.getUrl());
            request.setResponse(cliente.execute(method));
            new Thread(()->waitForGETResponse(request)).start();
        }catch(IOException e){
            LOG.log(Level.SEVERE, "Error executing GET, cause: {0}",e.getMessage());
            sendErrorMessage(request);
        }
    }
    
    private final ObjectMapper mapper;
    private final List<HttpGETResponseListener> getListeners;
    private final List<HttpPUDResponseListener> pudListeners;
    private final String REQUEST_ERROR;
    private final String REQUEST_SUCCESSFUL;
    private static final Logger LOG = Logger.getLogger(HttpHandler.class.getName());
}
