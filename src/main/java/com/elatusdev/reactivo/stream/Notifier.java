/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.elatusdev.reactivo.httpcliente.HttpMethods;
import com.elatusdev.reactivo.streamImpl.Subscriber;

/**
 *
 * @author root
 */
public interface Notifier extends HttpGETResponseListener,
                                  HttpPPDResponseListener,
                                  HttpMethods{
    public void start();
    
    public void sendData(Class<?> entity);
    
    public boolean isInShortTermCache(Class<?> cls);
    
    public boolean isInLongTermCache(Class<?> cls);
    
    public void addLongTermCache(Class<?> entity);
    
    public void addShortTermCache(Class<?> entity);
            
    public void addMapEntitiesRef(Class<?> cls, TypeReference<?> ref);
            
    public void addSubscriber(Subscriber subscriptor);
}
