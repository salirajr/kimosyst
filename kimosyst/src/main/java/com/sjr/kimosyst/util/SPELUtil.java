/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *
 * @author JOVIR
 */
public class SPELUtil {

    private final SpelParserConfiguration config;

    private final SpelExpressionParser parser;

    private StandardEvaluationContext context;

    public SPELUtil() {
        config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());
        parser = new SpelExpressionParser(config);
    }

    public boolean xCVlte(String val, String ipt, String expression) {
        context = new StandardEvaluationContext();
        context.setVariable("V", val);
        context.setVariable("in", ipt);
        return parser.parseExpression(expression).getValue(context, Boolean.class);
    }

    public Object xCXcte(String val, String ipt, String expression) {
        context = new StandardEvaluationContext();
        context.setVariable("V", val);
        context.setVariable("in", ipt);
        return parser.parseExpression(expression).getValue(context);
    }

    public Object xVal(String expression) {
        Expression expr = parser.parseExpression(expression);
        return expr.getValue();
    }

    public boolean xVlte(String expression) {
        Expression expr = parser.parseExpression(expression);
        return expr.getValue((Boolean.class));
    }

    public String evaluateCVlte(String expression) {
        try {
            xCVlte("qwertyuiopasdfghjklzxcvbnm", "0", expression);
            return "00";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /*public static void main(String args[]) {
        System.out.println(new SPELUtil().evaluateCVlte("#V.contains(#in)"));
        System.out.println(new SPELUtil().evaluateCVlte("#V.contains(#in.toUpperCase())"));
    }*/
}
