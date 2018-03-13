/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.any;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author JOVIR
 */
public class ScriptEngineTest {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine =  mgr.getEngineByName("JavaScript");
        String foo = "12 * (12 + 1)";
        System.out.println(engine.eval(foo));

    }
}
