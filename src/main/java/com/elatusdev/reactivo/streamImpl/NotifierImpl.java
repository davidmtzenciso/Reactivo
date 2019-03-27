/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.streamImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestGET;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestPPD;
import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.elatusdev.reactivo.stream.Notifier;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author root
 */
public class NotifierImpl extends Thread implements Notifier {
    
    public NotifierImpl() {
        ERROR_MSG = "Error interno, favor de contactar soporte técnico";
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapEntityCollection = new HashMap<>();
        shortTermEntities = new LinkedList<>();
        longTermEntities = new LinkedList<>();
        shortTermTimers = new HashMap<>();
        longTermData = new HashMap<>();
        shortTermData = new HashMap<>();
        subscribers = new LinkedList<>();
    }
    
    @Override
    public void onGETError(Class<?> type, int status){
        sendData(type, null, Integer.toString(status));
    }
    
    @Override
    public void onPPDError(Class<?> type, int status){
        sendData(type, null, Integer.toString(status));
    }
    
    @Override
    public void onGETResponse(HttpEvent<HttpRequestGET> evt) {
        List<Object> data;
        Class<?> type;
        
        data = null;
        type = evt.getRequest().getType();
        try {
            data = mapResponse(evt.getRequest().getResponse().getEntity().getContent(),
                    evt.getRequest().getType());
        } catch (IOException | NullPointerException
                | ClassCastException e) {
            LOG.log(Level.SEVERE, "Error mapping {0}, cause:{1}",
                    new Object[]{type.getSimpleName(), e});
        }

        if(evt.getRequest().hasProgressUpdater())
            evt.getRequest().getProgressUpdater().accept(100);
        
        cacheData(type, data);
        sendData(type, data, null);
    }
    
    private List<Object> mapResponse(InputStream json, Class<?> type) throws IOException,
            NullPointerException, ClassCastException {
        return mapper.readValue(json, mapEntityCollection.get(type));
    }
    
    private void cacheData(Class<?> type, List<Object> data) {
        Timer timer;
        
        if (data != null) {
            if (isShortTermEntity(type)) {
                LOG.info("caching short term..");
                shortTermData.put(type, data);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        shortTermData.remove(type);
                    }
                }, 10 * 60 * 1000);
                shortTermTimers.put(type, timer);
            } else if (isLongTermEntity(type)) {
                LOG.info("caching long term..");
                longTermData.put(type, data);
            }
        }
        else
            LOG.info("Error mapping data");
    }
    
    @Override
    public void onPPDResponse(HttpEvent<HttpRequestPPD> evt) {
        Class<?> type;
        Object object;
        
        type = evt.getRequest().getElement().getClass();
        object = evt.getRequest().getElement();
        switch (evt.getRequest().getMethod()) {
            case POST:                
                saveObject(object, type);
                break;
            case PUT:
                saveObject(object, type);
                break;
            case DELETE:
                removeObject(object, type);
                break;
            default:
                LOG.severe("Error on PPD response, http method no supported!");
        }
    }
    
    private void removeObject(Object object, Class<?> type){
       if(this.isInLongTermCache(type)){
            this.longTermData.remove(type);
        }
        else if(this.isInShortTermCache(type)){
            this.shortTermData.remove(type);
        } 
    }
    
    private void saveObject(Object object, Class<?> type){
       if(this.isInLongTermCache(type)){
            this.longTermData.get(type).add(object);
        }
        else if(this.isInShortTermCache(type)){
            this.shortTermData.get(type).add(object);
        } 
    }
    
    @SuppressWarnings("unchecked")
    private void sendData(Class<?> type, List<Object> data, String error) {        
        Subscriber subscriber;

        for (int i=0; i < subscribers.size(); i++) {
            subscriber = subscribers.get(i);
            if (subscriber.getType().equals(type)) {
                if (!subscriber.isContinuos()) {
                    subscribers.remove(i);
                }
                if (data != null) {
                    for (int j = 0; j < data.size(); j++) {
                        notifySubscriber(subscriber, data.get(j));
                    }
                } 
                else if (subscriber.getErrorConsumer() != null) {
                    if(error == null)
                        subscriber.getErrorConsumer().accept(ERROR_MSG);
                    else
                        subscriber.getErrorConsumer().accept(error);
                }
            }
        }
    }
    
    private void notifySubscriber(Subscriber subscriber, Object data) {
        if(subscriber.getResponseConsumer() != null){
            if(subscriber.getFilter() == null)
                subscriber.getResponseConsumer().accept(data);
            else if(subscriber.getFilter().test(data))
                    subscriber.getResponseConsumer().accept(data);
        }
    }
    
    private boolean isLongTermEntity(Class<?> type) {
        return longTermEntities.stream().anyMatch(entity -> entity.equals(type));
    }
    
    private boolean isShortTermEntity(Class<?> type) {
        return shortTermEntities.stream().anyMatch(entity -> entity.equals(type));
    }
    
    @Override
    public void addSubscriber(Subscriber subscriptor) {
        this.subscribers.add(subscriptor);
    }
    
    @Override
    public void addMapEntitiesRef(Class<?> cls, TypeReference<?> ref) {
        mapEntityCollection.put(cls, ref);
    }
    
    @Override
    public void addLongTermCache(Class<?> entity) {
        longTermEntities.add(entity);
    }
    
    @Override
    public void addShortTermCache(Class<?> entity) {
        shortTermEntities.add(entity);
    }
    
    @Override
    public boolean isInShortTermCache(Class<?> cls) {
        return shortTermData.keySet().stream()
                .anyMatch(entity -> entity.equals(cls));
    }
    
    @Override
    public boolean isInLongTermCache(Class<?> cls) {
        return longTermData.keySet().stream()
                .anyMatch(entity -> entity.equals(cls));
    }
    
    @Override
    public void sendData(Class<?> entity) {
        LOG.info("calling send data!");
        if (isInLongTermCache(entity)) {
            sendData(entity, longTermData.get(entity), null);
        } 
        else if (isInShortTermCache(entity)) {
            sendData(entity, shortTermData.get(entity), null);
        }
    }
    
    private final String ERROR_MSG;
    private final ObjectMapper mapper;
    private final List<Subscriber> subscribers;
    private final List<Class<?>> shortTermEntities;
    private final List<Class<?>> longTermEntities;
    private final Map<Class<?>, List<Object>> longTermData;
    private final Map<Class<?>, Timer> shortTermTimers;
    private final Map<Class<?>, List<Object>> shortTermData;
    private final Map<Class<?>, TypeReference<?>> mapEntityCollection;
    private static final Logger LOG
            = Logger.getLogger(NotifierImpl.class.getName());
}
