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
public class HttpRequestGET {
    private final Consumer<Integer> progressUpdater;
    private final String url;
    private final Class<?> type;
    private HttpResponse response;
    
    public HttpRequestGET(Class<?> type, String url, Consumer<Integer> progressUpdater){
        this.url = url;
        this.type = type;
        this.progressUpdater = progressUpdater;
    }

    public Consumer<Integer> getProgressUpdater() {
        return progressUpdater;
    }
    
    public boolean hasProgressUpdater(){
        return progressUpdater != null;
    }

    public String getUrl() {
        return url;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public Class<?> getType() {
        return type;
    }

    public HttpResponse getResponse() {
        return response;
    }
    
    
}
