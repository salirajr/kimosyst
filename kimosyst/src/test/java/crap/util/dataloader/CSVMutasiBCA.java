/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.dataloader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author salirajr
 */
public class CSVMutasiBCA {
    
    public static void main(String[] args) {
        
        String csvFile = "/home/salirajr/Downloads/FEBRIANT1550_23172007.CSV";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        char inTy = 'E';
        
        try {
            
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] mutasibca = line.split(cvsSplitBy);
                
                String date = mutasibca[0];
                String text = mutasibca[1];
                String code = mutasibca[2];
                String nom = mutasibca[3];
                String type = mutasibca[4];
                String balance = mutasibca[5];
                if (text.contains("KARTU DEBIT FARMERS MAR") || text.contains("KARTU DEBIT GIANT") || text.contains("KARTU DEBIT CARREFOUR")) {
                    if (inTy == 'G') {
                        System.out.println("G-date=" + date + " , text=" + text + " , code=" + code + " , nom=" + nom + " , type=" + type + " , balance=" + balance);
                    }
                } else if (text.contains("KE 002 HERLIANI") || text.contains("KE 008 SERAP MANNIPPI ") || text.contains("KE 008 NISPUDEWA")) {
                    if (inTy == 'O') {
                        System.out.println("O-date=" + date + " , text=" + text + " , code=" + code + " , nom=" + nom + " , type=" + type + " , balance=" + balance);
                    }
                } else if (inTy == 'E') {
                    System.out.println("E-date=" + date + " , text=" + text + " , code=" + code + " , nom=" + nom + " , type=" + type + " , balance=" + balance);
                }
                
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
