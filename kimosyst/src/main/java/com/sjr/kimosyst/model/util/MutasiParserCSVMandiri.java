/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sjr.kimosyst.model.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author JOVIR
 */
@Component
@Data
public class MutasiParserCSVMandiri {

    @Value("${mutasi.keysHeaders}")
    private String keysHeaders;

    @Value("${mutasi.keysContent}")
    private String keysContent;

    @Value("${mutasi.keysContent.validity}")
    private String keysContentValidity;

    public String caps(String val) {
        return "|" + val + "|";
    }

    public String trim(String val) {
        return val.trim();
    }

    public String cls(String val) {
        return val.replaceAll("\\s+", "");
    }

    public BaseModel doParse(byte[] csv) throws IOException {

        Map headers = new HashMap<>(), temp = new HashMap<>();
        List body = new ArrayList();

        String[] keysContents = keysContent.split("\\|");
        String[] keysContentValidities = keysContentValidity.split("\\|");
        System.out.println(Arrays.toString(keysContents));
        try (Reader csvFile = new InputStreamReader(new ByteArrayInputStream(csv))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvFile);
            int isANewBlock;
            String at0, at2, state = "0bl", hkval, tHkval, hkvalidity;
            for (CSVRecord record : records) {
                at0 = record.get(0);
                System.out.println("at0=" + at0);
                if ((state.equals("0bl") || state.equals("1bl")) && caps(keysHeaders).contains(caps(at0))) {
                    at2 = record.get(2);
                    System.out.println("as [" + state + "]" + at0 + " : " + at2);
                    /* Assign Headers*/
                    headers.put(cls(at0), at2);
                } else {
                    if (state.equals("0bl") && caps(keysContent).contains(caps(at0))) {
                        int ihk = 0;
                        for (String hk : keysContents) {
                            if (!hk.equals(record.get(ihk++))) {
                                break;
                            }
                        }
                        if (ihk == keysContents.length) {
                            state = "Mbl";
                            System.out.println("EXITING 0bl to Mbl[" + state + "]");
                        }
                    } else if (state.equals("Mbl")) {

                        isANewBlock = 0;
                        for (int ihk = 0; ihk < keysContents.length; ihk++) {
                            hkval = trim(record.get(ihk));
                            hkvalidity = trim(keysContentValidities[ihk]);
                            if (hkvalidity.equals("1") && !hkval.equals("")) {
                                isANewBlock++;
                            }
                        }
                        System.out.println("isANewBlock=" + isANewBlock);
                        if (isANewBlock == keysContents.length) {
                            if (!temp.isEmpty()) {
                                System.out.println("++ADD TO BODY[" + temp + "]");
                                body.add(temp);
                            }
                            temp = new HashMap();
                            for (int ihk = 0; ihk < keysContents.length; ihk++) {
                                String hk = cls(keysContents[ihk]);
                                hkval = trim(record.get(ihk));
                                temp.put(hk, hkval);
                            }
                            System.out.println("++NEW MAP DEFINDED[" + temp + "]");

                        } else if (isANewBlock < keysContents.length && isANewBlock > 0) {
                            System.out.println("EXPANDED TO CURRENT MAP");
                            for (int ihk = 0; ihk < keysContents.length; ihk++) {
                                String hk = cls(keysContents[ihk]);
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
        return new BaseModel(body, headers);

    }

    public BaseModel doParse(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] data = Files.readAllBytes(path);
        return doParse(data);

    }
}
