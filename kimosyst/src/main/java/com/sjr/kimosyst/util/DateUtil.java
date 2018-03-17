/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author salirajr
 */
public class DateUtil {

    public static SimpleDateFormat fmtDate = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat fmtTDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static long ADAYINMS = 86400000L;//60L * 60L * 24L * 1000L;
    public static long AMINUTEINMS = 60000L;
    public static long AMONTHINMS = 2678400000L;
    public static String FORMATMEMO = "ddMM yyyy HH:mmss";

    public static String FORMATCHIPER = "ddMMyyyy";

    public static Timestamp getTToday00() {
        long now = new Date().getTime();
        try {
            now = fmtDate.parse(fmtDate.format(new Date())).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Timestamp(now);
    }

    public static Timestamp getTToday23() {
        long now = new Date().getTime();
        try {
            now = fmtDate.parse(fmtDate.format(new Date())).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Timestamp(now + ADAYINMS - AMINUTEINMS);
    }

    public static Timestamp getTNow() {
        return new Timestamp(new Date().getTime());
    }

    public static String getMemoTNow() {
        return new SimpleDateFormat(FORMATMEMO).format(new Date());
    }

    public static java.sql.Date format(String text) throws ParseException {
        return new java.sql.Date(fmtDate.parse(text).getTime());
    }

    public static Timestamp format(String text, SimpleDateFormat format) throws ParseException {
        return new Timestamp(format.parse(text).getTime());
    }

    public static java.sql.Date getDToday() {
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public static String asMemo(Date dt) {
        return new SimpleDateFormat(FORMATMEMO).format(dt);
    }

    public static String asMemo(java.sql.Date dt) {
        return new SimpleDateFormat(FORMATMEMO).format(dt);
    }

    public static String asMemo(Timestamp dt) {
        return new SimpleDateFormat(FORMATMEMO).format(dt);
    }

    public static String getChiperKey() {
        return new SimpleDateFormat(FORMATCHIPER).format(new Date());
    }

    public static long getTimesDiff(Timestamp a, Timestamp b) {
        return Math.abs(a.getTime() - b.getTime());
    }

    public static Timestamp addDays(Timestamp a, int b) {
        a.setTime(a.getTime() + (b * ADAYINMS));
        return a;
    }


}
