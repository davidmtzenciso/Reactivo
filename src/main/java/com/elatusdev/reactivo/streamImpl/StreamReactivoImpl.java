/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.streamImpl;

import com.elatusdev.reactivo.stream.Notifier;
import com.elatusdev.reactivo.stream.StreamReactivo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.elatusdev.reactivo.httpcliente.HttpGETRequestListener;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestGET;
import com.elatusdev.reactivo.httpclienteImpl.HttpHandlerImpl;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.elatusdev.reactivo.httpcliente.HttpHandler;
import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestPPD;
import com.elatusdev.reactivo.httpcliente.HttpPPDRequestListener;

/**
 *
 * @author root
 */
public class StreamReactivoImpl extends Thread implements StreamReactivo{

    public StreamReactivoImpl(){
        notifier = new NotifierImpl();
        notifier.start();
        httpHandler = new HttpHandlerImpl();
        httpHandler.setGETEventListener(notifier);       
        httpHandler.setPPDEventListener(notifier);
        getRequestListener = httpHandler;
        pudRequestListener = httpHandler;
    }
        
    @Override
    public StreamReactivo subscribePOST(String url, Object obj){
        subscriber = new Subscriber(obj.getClass());
        pendingPUDRequest = new HttpRequestPPD(url,POST,obj,null);
        return this;
    }
    
    @Override
    public StreamReactivo subscribePOST(String url, Object obj, 
                                        Consumer<Integer> progressUpdater){
        subscriber = new Subscriber(obj.getClass());
        pendingPUDRequest = new HttpRequestPPD(url,POST,obj,progressUpdater);
        return this;
    }
    
    @Override
    public StreamReactivo subscribeGET(Class<?> cls, String url, Consumer<Integer> progressUpdater){
        subscriber = new Subscriber(cls);
        pendingGETRequest = new HttpRequestGET(cls, url, progressUpdater);
        return this;
    }
    
    @Override
    public StreamReactivo subscribeGET(Class<?> cls, String url){
        subscriber = new Subscriber(cls);
        pendingGETRequest = new HttpRequestGET(cls, url, null);
        return this;
    }
    
    @Override
    public StreamReactivo subscribe(Class<?> cls){ 
        subscriber = new Subscriber(cls);
        pendingGETRequest = null;
        return this;
    }
    
    @Override
    public StreamReactivo onResponse(Consumer<Object> consumer){
        subscriber.setResponseConsumer(consumer);
        return this;
    }
    
    @Override
    public StreamReactivo onError(Consumer<String> consumer){
        subscriber.setErrorConsumer(consumer);
        return this;
    }

    @Override
    public StreamReactivo filterBy(Predicate<Object> filtro){
        subscriber.setFilter(filtro);
        return this;
    }
    
    @Override
    public void onComplete(){
        subscriber.setContinuos(false);
        if(pendingGETRequest != null)
            sendData(subscriber, pendingGETRequest);
        else if(pendingPUDRequest != null)
            pudRequestListener.onPPDRequestEvent(new HttpEvent<>(this,pendingPUDRequest));
            
    }
    
    @Override
    public Subscription getSubscription(){
        sendData(subscriber, pendingGETRequest);
        return new Subscription(subscriber);
    }
    
    private void sendData(Subscriber subscriber, HttpRequestGET request){
        Class<?> type;
        
        type = subscriber.getType();
 
        if(notifier.isInLongTermCache(type)){
            LOG.log(Level.SEVERE, "{0} is in long term cache..",type.getSimpleName());
            notifier.addSubscriber(subscriber);
            notifier.sendData(type);
        }
        else if( notifier.isInShortTermCache(type)){
            LOG.log(Level.INFO, "{0} is in short term cache",type.getSimpleName());
            notifier.addSubscriber(subscriber);
            notifier.sendData(type);
        }
        else if(request != null){
            notifier.addSubscriber(subscriber);
            getRequestListener.onGETRequestEvent(new HttpEvent<>(this,request));
        }
        else
            subscriber.getErrorConsumer().accept(
                    new StringBuilder().append("Data not available in cache ")
                                       .append("for: ").append(type.getSimpleName())
                                       .toString());
    }

    @Override
    public StreamReactivo addMapEntitiesRef(Class<?> cls, TypeReference<?> ref){
        notifier.addMapEntitiesRef(cls, ref);
        return this;
    }
    
    @Override
    public StreamReactivo addLongTermCache(Class<?> entity){
        this.notifier.addLongTermCache(entity);
        return this;
    }
    
    @Override
    public StreamReactivo addShortTermCache(Class<?> entity){
        notifier.addShortTermCache(entity);
        return this;
    }

    private Subscriber subscriber;
    private final Notifier notifier;
    private HttpRequestPPD pendingPUDRequest;
    private HttpRequestGET pendingGETRequest;
    private final HttpHandler httpHandler;
    private final HttpGETRequestListener getRequestListener;
    private final HttpPPDRequestListener pudRequestListener;
    private static final Logger LOG = Logger.getLogger(StreamReactivo.class.getName());

}

