/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.stream;

import com.elatusdev.reactivo.httpclienteImpl.HttpRequestPPD;
import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;

/**
 *
 * @author root
 */
public interface HttpPPDResponseListener {
    
    public void onPPDResponse(HttpEvent<HttpRequestPPD> evt);
    
    public void onPPDError(Class<?> cls, int status);
}
