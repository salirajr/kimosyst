/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 * @author JOVIR
 */
public class SPELUtils {

    private SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
            this.getClass().getClassLoader());

    private SpelExpressionParser parser = new SpelExpressionParser(config);
    
    
}
