/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.httpclienteImpl;

import java.util.EventObject;

/**
 *
 * @author root
 * @param <T>
 */
public class HttpEvent<T> extends EventObject{

    private static final long serialVersionUID = 1L;
    private final T request;
   
    
    public HttpEvent(Object source, T request) {
        super(source);
        this.request = request;
    }

    public T getRequest() {
        return request;
    }

}
