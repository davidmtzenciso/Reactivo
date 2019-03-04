/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreaderimpl;

import com.elatusdev.model.ReadResult;
import com.elatusdev.ssreader.SSReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author root
 */
public class SSReaderImpl implements SSReader {
    
    public SSReaderImpl(){
        formatter = new DataFormatter();
        entities = new HashMap<>();
        formatErrors = new HashMap<>();
        readResults = new HashMap<>();
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
    
    private Workbook createBookWithEntities(List<String> names){
        Map.Entry<Map.Entry<String,Class<?>>,Map<String,String>> entry;
        Workbook book;
        Sheet sheet;
        
        book = new HSSFWorkbook();
        for(String name : names){
            try{
                entry = entities.entrySet().stream().filter(e->
                    e.getKey().getKey().equals(name)).findFirst().get();
                sheet = book.createSheet(name);
                createRow(sheet, 1, entry.getValue());
            }catch(NullPointerException e){
                LOG.log(Level.SEVERE, "Error finding entity, cause:{0}",e);
            }
        }
        return book;
    }
    
    private Workbook createBook(){
        Workbook libro;
        Sheet hoja;

        libro = new HSSFWorkbook();
        for(Map.Entry<String,Class<?>> entry : entities.keySet()){
            hoja = libro.createSheet(entry.getKey());
            createRow(hoja, 0, entities.get(entry));
        }
        return libro;
    }
    
    private void createRow(Sheet hoja, int numFila ,Map<String,String> propiedades){
        Cell celda;
        Row fila;
        int i = 0;
        
        fila = hoja.createRow(numFila);
        for(String key : propiedades.keySet()){
            celda = fila.createCell(i, CellType.STRING);
            celda.setCellValue(key);
            i++;
        }
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
    
    @SuppressWarnings("unchecked")
    private void transformSheet(Sheet sheet) throws NoSuchElementException {
        Map.Entry<Map.Entry<String,Class<?>>,Map<String,String>> entry;
        List<Object> objects;
        Class<?> cls;

        entry = entities.entrySet().stream().filter(e1->
                    e1.getKey().getKey().equals(sheet.getSheetName()))
                            .findFirst().get();
        cls = entry.getKey().getValue();
        objects = transformRows(sheet,entry.getKey(),entry.getValue()); 
        process.accept(cls, objects);
    }
   
    @SuppressWarnings("unchecked")
    private List<Object> transformRows(Sheet sheet,
                                            Map.Entry<String,Class<?>> entidad,
                                            Map<String,String> propiedades)
                                            throws NoSuchElementException {
        List<String> identificadores;
        Iterator<Row> filas;
        String sheetName;
        
        sheetName = sheet.getSheetName();
        filas = sheet.rowIterator();
        identificadores = transformToIdentifiers(sheetName,filas.next(),propiedades);
        if(identificadores == null){
            throw new NoSuchElementException("incomplete identifiers in sheet:"+sheetName);
        }
        else
            return transformarFilasAObjetos(sheetName,entidad.getValue(),filas,
                                                identificadores, propiedades);
    }
    
    @SuppressWarnings("unchecked")
    private List<Object> transformarFilasAObjetos(String sheetName, Class<?> entity, Iterator<Row> rows, 
                    List<String> identificadores, Map<String,String> propiedades)
                    throws NoSuchElementException {
        List<String> results;
        List<Object> list;
        int i;
        
        list = new ArrayList<>();
        results = new ArrayList<>();
        for(i=1; rows.hasNext(); i++){
            try{
                list.add(crearObjecto(identificadores,
                                    propiedades,entity,
                                    rows.next()));
                results.add(new StringBuilder().append("Fila ")
                                    .append(i).append(": aceptada").toString());
            }catch(NullPointerException e){
                results.add(new StringBuilder().append("Fila ")
                                    .append(i).append(": rechazada, ").append(e.getMessage())
                                    .toString());
            }
        }
        
        readResults.put(sheetName, results);
        if(list == null)
            throw new NoSuchElementException("Cero rows were transformed");
        else
            return list;
    }
    
    private List<String> transformToIdentifiers(String sheetName, Row fila, Map<String,String> properties){
        List<String> identifiers;
        List<String> errors;
        Iterator<Cell> celdas;
        String value;
        
        errors = new ArrayList<>();
        identifiers = new ArrayList<>();
        celdas = fila.cellIterator();
        while(celdas.hasNext()){
            value = formatter.formatCellValue(celdas.next());
            if(properties.containsKey(value))
                identifiers.add(value);
            else{
                errors.add(new StringBuilder()
                        .append("Valor no reconocido: ").append(value).toString());
            }
        }
        formatErrors.put(sheetName, errors);
        return identifiers.size() == properties.size() ? identifiers : null;
    }
    
    private Object crearObjecto(List<String> identificadores,Map<String,String> metodos,
                                Class<?> tipo, Row fila) throws NullPointerException {
        Object obj;
        
        obj = createObj.apply(tipo);
        for(int i=0; i<identificadores.size(); i++){
            try{
                ejecutarMetodo(obj,metodos.get(identificadores.get(i)),
                                formatter.formatCellValue(fila.getCell(i)));
            }catch(NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | NoSuchFieldException e){
                LOG.log(Level.SEVERE, "Error creating object class:{0}, cause:{1}",
                        new Object[]{tipo.getSimpleName(), e});
                throw new NullPointerException("Error inteno");
            } catch(NumberFormatException ex){
                throw new NullPointerException(new StringBuilder()
                        .append("Error en celda con identificador: ")
                        .append(identificadores.get(i))
                        .append(", causa: ").append(ex.getMessage()).toString());
            }
        }
        return obj;
    }
    
    private void ejecutarMetodo(Object target, String methodName, String valor)
            throws NoSuchMethodException, IllegalAccessException,
                    InvocationTargetException,NumberFormatException,
                    NoSuchFieldException {
                    
        Method metodo;
        Field field;
        Class<?> tipo;
        Class<?> clazz;
        String fieldName;

        clazz = target.getClass();
        fieldName = formatName(methodName);
        field = findField(fieldName, clazz);
        tipo = field.getType();
        try{
            metodo = clazz.getMethod(methodName, tipo);
        }catch(NoSuchMethodException e){
            metodo = clazz.getSuperclass().getMethod(methodName, tipo);
        }
        metodo.invoke(target, convertirValor(tipo,valor));
    }
    
    private Field findField(String fieldName, Class<?> cls) throws NoSuchFieldException{
        Class<?> superCls;
        
        try{
            return cls.getDeclaredField(fieldName);
        }catch(NoSuchFieldException e){
            superCls = cls.getSuperclass();
            if(!superCls.equals(Object.class))
                return findField(fieldName, superCls);
            throw
                 new NoSuchFieldException("field: "+fieldName+" not found in the class nor in any parent class");
        }
    }
    
    private String formatName(String methodName){
        String name =  methodName.substring(3, methodName.length());
        return String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1, name.length());
    }
    
    private Object convertirValor(Class<?> tipo, String valor) 
                                            throws NumberFormatException{
        try{
        if(tipo.equals(Integer.class))
            return Integer.parseInt(valor);
        else if(tipo.equals(Double.class))
            return Double.parseDouble(valor);
        else if(tipo.equals(Long.class))
            return Long.parseLong(valor);
        else if(tipo.equals(Boolean.class))
            return Integer.parseInt(valor) == 1;
        else
            return valor;
        }catch(NumberFormatException e){
            throw new NumberFormatException("valor erroneo");
        }
    }
    
    @Override
    public ReadResult readFile(File file){
        Iterator<Sheet> sheets;
        boolean fileError;
        
        try{
            fileError = false;
            readResults.clear();
            formatErrors.clear();
            sheets = getSheets(file);
            while(sheets.hasNext()){     
                try{
                    transformSheet(sheets.next());
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
    
   
    @Override
    public void addEntityMap(Map.Entry<String,Class<?>> entity, Map<String, String> properties){
        this.entities.put(entity, properties);
    }
  
    @Override
    public boolean createWholeBook(String fileName){
        return createFile(createBook(), fileName);
    }
    
    @Override
    public boolean createBookWithEntities(List<String> entidades, String fileName){
        return createFile(createBookWithEntities(entidades),fileName);
    }
    
    @Override
    public void setObjCreator(Function<Class<?>,Object> createObj){
        this.createObj = createObj;
    }
    
    @Override
    public void setOnSheetRead(BiConsumer<Class<?>,List<Object>> process){
        this.process = process;
    }
    
    private final DataFormatter formatter;
    private Function<Class<?>, Object> createObj;
    private BiConsumer<Class<?>, List<Object>> process;
    private final Map<String, List<String>> formatErrors;
    private final Map<String,List<String>> readResults;
    private final Map<Map.Entry<String,Class<?>>,Map<String, String>> entities;
    private static final Logger LOG = Logger.getLogger(SSReader.class.getName());
}
