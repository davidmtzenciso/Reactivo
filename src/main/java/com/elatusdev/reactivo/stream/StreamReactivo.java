/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.elatusdev.reactivo.httpcliente.HttpMethods;
import com.elatusdev.reactivo.streamImpl.Subscription;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author root
 */
public interface StreamReactivo extends HttpMethods{
    
    public void start();
        
    public StreamReactivo addMapEntitiesRef(Class<?> cls, TypeReference<?> ref);
    
    public StreamReactivo subscribePOST(String url, Object obj, Consumer<Integer> progressUpdater);    
    
    public StreamReactivo subscribePOST(String url, Object obj);   
    
    public StreamReactivo subscribeGET(Class<?> cls, String url, Consumer<Integer> updater);
    
    public StreamReactivo subscribeGET(Class<?> cls, String url);
    
    public StreamReactivo subscribe(Class<?> cls);
    
    public StreamReactivo onResponse(Consumer<Object> consumer);
    
    public StreamReactivo onError(Consumer<String> consumer);
    
    public StreamReactivo filterBy(Predicate<Object> filtro);
    
    public void onComplete();
    
    public Subscription getSubscription();
    
    public StreamReactivo addShortTermCache(Class<?> entity);
    
    public StreamReactivo addLongTermCache(Class<?> entity);
        
}

