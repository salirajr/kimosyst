/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.dataloader;

import com.sjr.kimosyst.model.util.BaseModel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author JOVIR
 */
public class CSVParsersVrCpy {

    public static String caps(String val) {
        return "|" + val + "|";
    }

    public static String trim(String val) {
        return val.trim();
    }

    public static String cls(String val) {
        return val.replaceAll("\\s+", "");
    }
    
    public static Object cast(Object val) {
        System.out.println("Class="+val.getClass());
        return val;
    }
    

    public static void main(String args[]) throws FileNotFoundException, IOException {

        Map headers = new HashMap<>(), temp = new HashMap<>();
        List body = new ArrayList();

        String key = "Saldo Awal|Total Kredit|Total Debet|Saldo Akhir|Nomor Transaksi|Nomor Rekening|Jenis Rekening|Periode Transaksi|Tampilkan Berdasarkan|Urutkan Berdasarkan";
        // Header Key
        String hkey = "Tanggal|Keterangan Transaksi|Debet|Kredit";
        String[] hkeys = hkey.split("\\|");
        // Header Key Validity
        String hkeyv = "1|1|1|1";
        String[] hkeysv = hkeyv.split("\\|");
        System.out.println(Arrays.toString(hkeys));
        Map<String, Object> kvPairs = new HashMap<String, Object>();
        String fileName = "E:\\Branches\\kampf\\kimosyst\\forms\\Book1trial-copy.csv";

        FileReader fileReader = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(fileReader);

        int isANewBlock = 0;
        String at0, state = "0bl", hkval, tHkval, hkvalidity;
        Object at2;
        for (CSVRecord record : records) {
            at0 = record.get(0);
            System.out.println("at0=" + at0);
            if ((state.equals("0bl") || state.equals("1bl")) && caps(key).contains(caps(at0))) {

                at2 = cast(record.get(2));
                System.out.println("as [" + state + "]" + at0 + " : " + at2);
                /* Assign Headers*/
                headers.put(cls(at0), at2);
            } else {
                if (state.equals("0bl") && caps(hkey).contains(caps(at0))) {
                    int ihk = 0;
                    for (String hk : hkeys) {
                        if (!hk.equals(record.get(ihk++))) {
                            break;
                        }
                    }
                    if (ihk == hkeys.length) {
                        state = "Mbl";
                        System.out.println("EXITING 0bl to Mbl[" + state + "]");
                    }
                } else if (state.equals("Mbl")) {

                    isANewBlock = 0;
                    for (int ihk = 0; ihk < hkeys.length; ihk++) {
                        hkval = trim(record.get(ihk));
                        hkvalidity = trim(hkeysv[ihk]);
                        if (hkvalidity.equals("1") && !hkval.equals("")) {
                            isANewBlock++;
                        }
                    }
                    System.out.println("isANewBlock=" + isANewBlock);
                    if (isANewBlock == hkeys.length) {
                        if (!temp.isEmpty()) {
                            System.out.println("++ADD TO BODY[" + temp + "]");
                            body.add(temp);
                        }
                        temp = new HashMap();
                        for (int ihk = 0; ihk < hkeys.length; ihk++) {
                            String hk = trim(hkeys[ihk]);
                            hkval = trim(record.get(ihk));
                            temp.put(hk, hkval);
                        }
                        System.out.println("++NEW MAP DEFINDED[" + temp + "]");

                    } else if (isANewBlock < hkeys.length && isANewBlock > 0) {
                        System.out.println("EXPANDED TO CURRENT MAP");
                        for (int ihk = 0; ihk < hkeys.length; ihk++) {
                            String hk = trim(hkeys[ihk]);
                            hkval = trim(record.get(ihk));
                            if (!hkval.equals("")) {
                                hkval = temp.get(hk) + " " + hkval;
                                temp.put(hk, hkval);
                                System.out.println("[" + hk + "] = " + hkval);
                            }

                        }
                    } else {
                        if (!temp.isEmpty()) {
                            System.out.println("++FINAL ADD TO BODY[" + temp + "]");
                            body.add(temp);
                        }
                        state = "1bl";
                        System.out.println("EXITING Mbl to 1bl[" + state + "]");
                    }

                }
            }

        }

    }

}
