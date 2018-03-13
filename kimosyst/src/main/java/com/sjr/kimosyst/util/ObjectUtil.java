/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

/**
 *
 * @author salirajr
 */
public class ObjectUtil {

    public static boolean isNull(Object in) {
        return in == null;
    }

    public static String asString(Object in) {
        return String.valueOf(in);
    }
}
