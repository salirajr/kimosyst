package com.sjr.kimosyst.service.poi;

import com.sjr.kimosyst.util.StringUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.portable.ApplicationException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author salirajr
 */
public class XLSToListOfMap {

    public RawSheet getList(String file) throws IOException, Exception {
        RawSheet result = new RawSheet();
        System.out.println(file);
        int yMIPointer = 0, xMIPointer = 1;
        Workbook workbook = toWorkbook(file);

        Sheet metainfo = workbook.getSheet("METAINFO");
        int nMIRows = metainfo.getPhysicalNumberOfRows();

        Row miRow;
        while (++yMIPointer < nMIRows) {
            miRow = metainfo.getRow(yMIPointer);
            result.putMetaInfo(miRow.getCell(xMIPointer).getStringCellValue(), miRow.getCell(xMIPointer + 1));
        }

        Sheet submission = workbook.getSheet("SUBMISSION");
        int nSRows = submission.getPhysicalNumberOfRows();
        int ySPointer = 1, xSPointer = 1;

        Row sRow;
        sRow = submission.getRow(ySPointer);
        int nSCell = sRow.getPhysicalNumberOfCells();
        List<String> keys = new ArrayList<>();
        int xTemp = xSPointer;
        while (xTemp < nSCell) {
            keys.add(sRow.getCell(xTemp++).getStringCellValue());
        }
        result.keys = keys;

        while (++ySPointer < nSRows) {
            sRow = submission.getRow(ySPointer);
            Map tMap = new HashMap<>();
            xTemp = xSPointer;
            while (xTemp < (keys.size() + xSPointer)) {
                tMap.put(keys.get(xTemp - xSPointer), asIs(sRow.getCell(xTemp++)));
            }
            result.content.add(tMap);
        }

        return result;

    }

    public Object asIs(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public Workbook toWorkbook(String fileLocation) throws IOException {
        FileInputStream fis;
        fis = new FileInputStream(fileLocation);
        Workbook result = new HSSFWorkbook(fis);
        fis.close();
        return result;
    }

}
