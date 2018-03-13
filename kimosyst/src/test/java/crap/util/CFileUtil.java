/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util;

import com.sjr.kimosyst.util.FileUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author JOVIR
 */
public class CFileUtil {
    public static void main(String args[]){
        String fileName= "C:\\Users\\jovir\\Desktop\\_temp\\x\\v\\y.txt";
        System.out.println(FilenameUtils.getBaseName(fileName));
        System.out.println(FilenameUtils.getPath(fileName));
        System.out.println(FilenameUtils.getFullPathNoEndSeparator(fileName));
        
        try {
            FileUtil.storeAsFile(fileName, "asdfasdfaf".getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CFileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
