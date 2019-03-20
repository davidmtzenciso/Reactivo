/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.httpcliente;

import com.elatusdev.reactivo.httpclienteImpl.HttpEvent;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestPPD;

/**
 *
 * @author root
 */
public interface HttpPPDRequestListener {
    
    public void onPPDRequestEvent(HttpEvent<HttpRequestPPD> request);
}
