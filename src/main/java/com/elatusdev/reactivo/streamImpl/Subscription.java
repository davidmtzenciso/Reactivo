/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.streamImpl;


/**
 *
 * @author root
 */
public class Subscription {
    
    private final Subscriber subscriptor;
    
    public Subscription(Subscriber subscriptor){
        this.subscriptor = subscriptor;
    }

    public void cancelar(){
        this.subscriptor.setContinuos(false);
    }

}
