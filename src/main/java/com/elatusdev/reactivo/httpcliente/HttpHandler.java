/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.httpcliente;

import com.elatusdev.reactivo.stream.HttpPUDResponseListener;
import com.elatusdev.reactivo.stream.HttpGETResponseListener;
import com.elatusdev.reactivo.httpclienteImpl.HttpRequestGET;
import java.util.function.Consumer;


/**
 *
 * @author root
 */
public interface HttpHandler extends HttpGETRequestListener, HttpPUDRequestListener {
    
    public void setGETEventListener(HttpGETResponseListener listener);
    
    public void setPUDEventListener(HttpPUDResponseListener listener);
           
}
