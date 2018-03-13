/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author JOVIR
 */
public class FileUtil {

    public static String BASEDIR;// = "E:\\Branches\\kampf\\kimosyst\\BDIR";

    public static String BASEDIR_IDTFR;// = "\\";

    public static int storeAsFile(String fileLocation, byte[] content) throws FileNotFoundException, IOException {
        new File(fileLocation).getParentFile().mkdirs();
        try (OutputStream outputStream = new FileOutputStream(new File(fileLocation))) {
            outputStream.write(content);
            outputStream.flush();
        }

        return 777;
    }

    public static String getAbsDir(String svrfile) {
        return BASEDIR + svrfile;
    }

    public static String getDir(Date date, String rootName, String fileName) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return BASEDIR_IDTFR + rootName + BASEDIR_IDTFR + c.get(Calendar.YEAR) + StringUtil.padZero(c.get(Calendar.WEEK_OF_YEAR), 3) + BASEDIR_IDTFR + fileName;
    }

    public static void removeFile(String fileLoc) {
        File file = new File(fileLoc);
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
    }

    public static String getFileExtension(String fileLoc) {
        File file = new File(fileLoc);
        return file.getName().split("\\.")[1];
    }

    public static String getDir(String fileLoc) {
        File file = new File(fileLoc);
        return file.getParentFile().toString() + BASEDIR_IDTFR;
    }

    public static boolean isExist(String fileLocation) {
        return new File(fileLocation).exists();
    }

    /*public static void main(String[] args) {
        System.out.println(getFileExtension("c/c/d/d/sdfs.fas"));
        System.out.println(getDir(DateUtil.getDToday(), "x", "y"));
        System.out.println(getDir("c/c/asdfd/dasdfa/sdfs.fas"));
    }*/
}
