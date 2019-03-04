/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elatusdev.ssreaderimpl;

import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public class ReadResult {
    
    private final boolean fileError;
    private final Map<String,List<String>> results;
    private final Map<String, List<String>> formatErrors;

    public ReadResult(boolean fileError, Map<String, List<String>> results, Map<String, List<String>> formatErrors) {
        this.fileError = fileError;
        this.results = results;
        this.formatErrors = formatErrors;
    }
    
    public boolean hasFileError(){
        return fileError;
    }

    public Map<String, List<String>> getResults() {
        return results;
    }

    public Map<String, List<String>> getFormatErrors() {
        return formatErrors;
    }

}
