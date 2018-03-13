/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util;

import com.sjr.kimosyst.util.SPELUtil;
import com.sjr.kimosyst.util.SPELUtils;

/**
 *
 * @author JOVIR
 */
public class CSPEL {

    public static void main(String args[]) {
        SPELUtil util = new SPELUtil();

        System.out.println(util.xVlte("'1234567899ghjk345678'.contains('ghjk')"));
        
        System.out.println(util.xVlte("'1234567899ghjk345678'.contains('ghjk')"));
        
        System.out.println(util.xCVlte("1234567899ghjk345678", "ghjk", "#V.contains(#in)"));
    }
}
