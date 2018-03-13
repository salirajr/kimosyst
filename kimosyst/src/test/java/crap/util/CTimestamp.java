/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author salirajr
 */
public class CTimestamp {

    long adayinmilis = 86400000;

    public Timestamp v1() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        long time = dateFormat.parse(dateFormat.format(new Date())).getTime();
        return new Timestamp(time);
    }

    public static void main(String[] args) throws ParseException {
        
        System.out.println("MMST"+System.currentTimeMillis());
    }
}
