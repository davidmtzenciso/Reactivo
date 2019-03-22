/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreader;

import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author root
 */
public interface ElatusSSReader {
    
    public List<List<Object>> transformSheet(Sheet sheet) throws NullPointerException;
}
