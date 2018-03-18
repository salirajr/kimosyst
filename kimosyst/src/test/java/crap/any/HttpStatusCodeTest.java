/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.any;

import org.springframework.http.HttpStatus;

/**
 *
 * @author salirajr
 */
public class HttpStatusCodeTest {

    public static void main(String[] args) {
        Object x = HttpStatus.ALREADY_REPORTED;
        System.out.println(((HttpStatus) x) == HttpStatus.ALREADY_REPORTED);
    }
}
