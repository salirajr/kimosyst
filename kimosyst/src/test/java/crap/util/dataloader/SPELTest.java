/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.dataloader;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 * @author JOVIR
 */
public class SPELTest {

    SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
            this.getClass().getClassLoader());

    SpelExpressionParser parser = new SpelExpressionParser(config);
    Expression expr = parser.parseExpression("1==2");

    void run() {
        System.out.println(expr.getValue());
    }

    public static void main(String args[]) {

        SPELTest test = new SPELTest();
        test.run();

    }

}
