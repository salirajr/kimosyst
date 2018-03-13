/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.dataloader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author JOVIR
 */
public class CSVParsers {

    public static String caps(String val) {
        return "|" + val + "|";
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {

        String key = "Saldo Awal|Total Kredit|Total Debet|Saldo Akhir|Nomor Transaksi|Nomor Rekening|Jenis Rekening|Periode Transaksi|Tampilkan Berdasarkan|Urutkan Berdasarkan";
        String hkey = "Tanggal|Keterangan Transaksi|Debet|Kredit";
        String[] hkeys = hkey.split("\\|");
        System.out.println(Arrays.toString(hkeys));
        Map<String, Object> kvPairs = new HashMap<String, Object>();
        String fileName = "E:\\Branches\\kampf\\kimosyst\\forms\\Book1trial-from-server.csv";

        FileReader fileReader = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(fileReader);

        boolean isVMutasi = true;
        String at0, at1, state = "0bl";
        for (CSVRecord record : records) {
            at0 = record.get(0);
            System.out.println("at0=" + at0);
            if ((state.equals("0bl") || state.equals("1bl")) && caps(key).contains(caps(at0))) {
                at1 = record.get(1);
                System.out.println("as [" + state + "]" + at0 + " : " + at1);
            } else if (at0.trim().equals("")) {
                if (state.equals("Mbl")) {
                    int ihk = 0;
                    for (String hk : hkeys) {
                        if (!"".equals(record.get(ihk++))) {
                            break;
                        }
                    }
                    if (ihk == hkeys.length) {
                        state = "1bl";
                        System.out.println("EXITING Mbl to 1bl[" + state + "]");
                    }
                } else {
                    System.out.println("Empty on " + state);
                }

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
                    int ihk = 0;
                    for (String hk : hkeys) {
                        System.out.println(hk + "=" + record.get(ihk++));
                    }
                    System.out.println("++");
                }
            }

        }

    }

}
