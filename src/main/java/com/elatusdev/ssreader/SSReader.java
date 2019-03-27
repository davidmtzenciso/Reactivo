/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreader;

import com.elatusdev.model.ReadResult;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * @author root
 */
public interface SSReader {
    
    public ReadResult readFile(File file);
    
    public void addEntityMap(Map.Entry<String,Class<?>> entity, 
                                Map<String, String> properties);
      
    public boolean createWholeBook(String fileName);
    
    public boolean createBookWithEntities(List<String> entidades, String fileName);
    
    public void setObjCreator(Function<Class<?>,Object> createObj);
    
    public void setOnSheetRead(BiConsumer<Class<?>,List<Object>> process);
    
}
