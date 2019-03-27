/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.reactivo.streamImpl;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author root
 */
public class Subscriber {
    private final Class<?> type;
    private Consumer<Object> responseConsumer;
    private Consumer<String> errorConsumer;
    private Predicate<Object> filter;
    private boolean continuos;
    
    public Subscriber(Class<?> type){
        this.type = type;
        this.continuos = true;
    }

    public Consumer<String> getErrorConsumer() {
        return errorConsumer;
    }

    public void setErrorConsumer(Consumer<String> errorConsumer) {
        this.errorConsumer = errorConsumer;
    }

    public void setResponseConsumer(Consumer<Object> consumer) {
        this.responseConsumer = consumer;
    }

    public Consumer<Object> getResponseConsumer() {
        return responseConsumer;
    }

    public boolean isContinuos() {
        return continuos;
    }

    public Class<?> getType() {
        return type;
    }

    public void setContinuos(boolean continuos) {
        this.continuos = continuos;
    }

    public Predicate<Object> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<Object> predicate) {
        this.filter = predicate;
    }
 
}
