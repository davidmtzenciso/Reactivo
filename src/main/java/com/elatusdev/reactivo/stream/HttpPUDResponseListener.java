/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.stream;

import com.elatusdev.reactivo.httpclienteImpl.HttpRequestPUD;
import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;

/**
 *
 * @author root
 */
public interface HttpPUDResponseListener {
    
    public void onPUDResponse(HttpEvent<HttpRequestPUD> evt);
    
    public void onPUDError(Class<?> cls, String error);
}
