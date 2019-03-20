/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.httpclienteImpl;

import java.util.function.Consumer;
import org.apache.http.HttpResponse;

/**
 *
 * @author root
 */
public class HttpRequestPPD {
    private final String url;
    private final String method;
    private final Object element;
    private HttpResponse response;
    private final Consumer<Integer> progressUpdater;
    
    public HttpRequestPPD(String url, String method,
                            Object element, Consumer<Integer> progressUpdater){
        this.url = url;
        this.method = method;
        this.element = element;
        this.progressUpdater = progressUpdater;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Object getElement() {
        return element;
    }

    public HttpResponse getResponse() {
        return response;
    }
    
    public void setReponse(HttpResponse response){
        this.response = response;
    }

    public Consumer<Integer> getProgressUpdater() {
        return progressUpdater;
    }
    
    public boolean hasProgressUpdater(){
        return progressUpdater != null;
    }

}
