/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreaderimpl;

import com.elatusdev.ssreader.ElatusSSReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author root
 */
public class ElatusSSReaderImpl extends AbstractSSReader implements ElatusSSReader {
    
    @Override
    public List<List<Object>> transformSheet(Sheet sheet) throws NullPointerException{
        return transformSheet(sheet, entities.get(sheet.getSheetName())); 
    }
    
    @SuppressWarnings("unchecked")
    private List<List<Object>> transformSheet(Sheet sheet, Class<?> cls)
                                                        throws NoSuchElementException,
                                                               NullPointerException {
        List<List<Object>> data;
        Iterator<Row> rows;
        List<String> labels;
        String sheetName;
        Object object;
        
        data = null;
        sheetName = sheet.getSheetName();
        rows = sheet.rowIterator();
        object = instanceCreator.apply(cls);
        labels = getLabels.apply(object);
        if(validateColumnLabels(sheetName, rows.next(), labels)){
            data = new ArrayList<>();
            while(rows.hasNext()){
                data.add(readRow(rows.next()));
            }
        }
        return data;
    }
    
    private List<Object> readRow(Row row){
        Iterator<Cell> cells;
        List<Object> data;
        
        cells = row.cellIterator();
        data = new ArrayList<>();
        for(int i=0; cells.hasNext(); i++){
            data.add(formatter.formatCellValue(cells.next()));
        }
        return data;
    }
    
    private boolean validateColumnLabels(String sheetName, Row row, List<String> labels){
        List<String> columns;
        List<String> errors;
        Iterator<Cell> cells;
        String value;
        
        errors = new ArrayList<>();
        columns = new ArrayList<>();
        cells = row.cellIterator();
        while(cells.hasNext()){
            value = formatter.formatCellValue(cells.next());
            if(labels.contains(value))
                columns.add(value);
            else{
                errors.add(new StringBuilder()
                        .append("Value not recognized: ").append(value).toString());
            }
        }
        formatErrors.put(sheetName, errors);
        return columns.size() == labels.size();
    }
 
    @Override
    public boolean createWholeBook(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createBookWithEntities(List<String> entidades, String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Function<Object, List<String>> getLabels;
    private Function<Class<?>, Object> instanceCreator;
}