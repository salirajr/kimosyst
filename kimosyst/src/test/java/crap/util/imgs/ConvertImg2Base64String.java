/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.imgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author JOVIR
 */
public class ConvertImg2Base64String {
    public static void main(String[] args) {

           File f =  new File("C:\\Users\\jovir\\Downloads\\struk.jpg");
             String encodstring = encodeFileToBase64Binary(f);
             System.out.println(encodstring);
       }

       private static String encodeFileToBase64Binary(File file){
            String encodedfile = null;
            try {
                FileInputStream fileInputStreamReader = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                fileInputStreamReader.read(bytes);
                encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block

            } catch (IOException e) {
                // TODO Auto-generated catch block

            }

            return encodedfile;
        }
}
