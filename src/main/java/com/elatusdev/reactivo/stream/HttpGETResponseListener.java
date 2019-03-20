/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.stream;

import com.elatusdev.reactivo.httpclienteImpl.HttpRequestGET;
import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;

/**
 *
 * @author root
 */
public interface HttpGETResponseListener {
    
    public void onGETResponse(HttpEvent<HttpRequestGET> evt);
    
    public void onGETError(Class<?> type, int status);
        
}

