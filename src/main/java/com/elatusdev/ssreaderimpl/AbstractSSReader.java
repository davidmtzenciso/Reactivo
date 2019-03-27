/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreaderimpl;

import com.elatusdev.ssreader.model.ReadResult;
import com.elatusdev.ssreader.SSReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author root
 */
public abstract class AbstractSSReader {
    
    public AbstractSSReader(){
        formatter = new DataFormatter();
        formatErrors = new HashMap<>();
        readResults = new HashMap<>();
        results = new ArrayList<>();
    }
    
    private boolean createFile(Workbook book, String name){
        FileOutputStream outputStream;
        File file;
        
        try{
            file = new File(name);
            if(file.exists())
                file.delete();
            outputStream = new FileOutputStream(file);
            book.write(outputStream);
            outputStream.close();
            book.close();
            return true;
        }catch(IOException e){
            LOG.log(Level.SEVERE, "Erro creating excel, cause:{0}",e.getMessage());
        }
        return false;
    }
    
    private Iterator<Sheet> getSheets(File file) throws IOException,
                                                    FileNotFoundException {
        FileInputStream stream;
        
        if(file != null)
            stream = new FileInputStream(file);
        else
            throw new IOException("arg passed with null value");
        
        try{ return WorkbookFactory.create(stream).sheetIterator(); }
        catch(IOException e){
            return new HSSFWorkbook(stream).iterator();
        }
    }
  
    public ReadResult readFile(File file) {
        Iterator<Sheet> sheets;
        boolean fileError;
        
        try{
            fileError = false;
            readResults.clear();
            formatErrors.clear();
            sheets = getSheets(file);
            while(sheets.hasNext()){     
                try{
                    transformSheet.accept(sheets.next());
                }catch(NoSuchElementException  e){
                    LOG.log(Level.SEVERE,"{0} {1} Exception: {2}",
                    new Object[]{LocalDate.now(), LocalTime.now(),e});
                }
            }
        }catch(IOException e){
            fileError = true;
            LOG.log(Level.SEVERE,"{0} {1} Exception:{2}, msj:{3}",
                    new Object[]{LocalDate.now(), LocalTime.now(),e});
        }
        return new ReadResult(fileError,readResults, formatErrors);
    }

    public abstract boolean createWholeBook(String fileName);
    
    public abstract boolean createBookWithEntities(List<String> entidades, String fileName);
    
    public void setOnSheetRead(BiConsumer<Class<?>,List<Object>> process){
        this.process = process;
    }
    
    public void setTransformeSheet(Consumer<Sheet> consumer){
        this.transformSheet = consumer;
    }
   
    
    protected List<String> results;
    private Consumer<Sheet> transformSheet;
    protected final DataFormatter formatter;
    protected BiConsumer<Class<?>, List<Object>> process;
    protected final Map<String, List<String>> formatErrors;
    protected final Map<String,List<String>> readResults;
    protected static final Logger LOG = Logger.getLogger(SSReader.class.getName());

}
