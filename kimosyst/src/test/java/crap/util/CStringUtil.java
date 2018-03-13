/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util;

import com.sjr.kimosyst.util.StringUtil;

/**
 *
 * @author JOVIR
 */
public class CStringUtil {

    public static void main(String[] args) {
        Long num =new Long("23432345");
        String str = "mystring";
        System.out.println(String.format("%034d", num));
        
        System.out.println(StringUtil.padZero(num, 20));
    }
}
