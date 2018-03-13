/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author JOVIR
 */
public class ImageUtil {

    public static byte[] toArrayImgBytes(String base64Image) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
    }

    public static String toStringImgBytes(byte[] binary) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(binary);
    }

    public static String grabImgBytes(String fileLocation) throws FileNotFoundException, IOException {
        File file = new File(fileLocation);
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
    
     public static byte[] grabImgBytesArrays(String fileLocation) throws FileNotFoundException, IOException {
        File file = new File(fileLocation);
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStreamReader.read(bytes);
        return bytes;
    }

    public static String getImagHead(String type) {
        type = type.toLowerCase();
        if (type.contains("png")) {
            return "data:image/png;base64";
        } else if (type.contains("jpeg")) {
            return "data:image/jpeg;base64";
        } else if (type.contains("jpg")) {
            return "data:image/jpg;base64";
        } else {
            return "";
        }
    }

}
