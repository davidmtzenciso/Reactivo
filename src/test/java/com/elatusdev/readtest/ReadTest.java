/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.readtest;

import com.elatusdev.ssreader.SSReader;
import com.elatusdev.ssreaderimpl.ReadResult;
import com.elatusdev.ssreaderimpl.SSReaderImpl;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author root
 */
public class ReadTest {
    
    private SSReader reader;
    
    @Before
    public void init(){
        reader = new SSReaderImpl();
    }
    
    @Test
    public void readFile(){        
    }
}
