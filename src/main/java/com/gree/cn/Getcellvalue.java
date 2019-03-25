package com.gree.cn;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Getcellvalue  {
    public static  Object findCell(Cell cell) {
        if (cell == null) {
            return "";
        }

        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case FORMULA:
                try {
                    obj = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    String valueOf = String.valueOf(cell.getNumericCellValue());
                    BigDecimal bd = new BigDecimal(Double.valueOf(valueOf));
                    bd = bd.setScale(2, RoundingMode.HALF_UP);
                    obj = bd;
                }
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                String value = String.valueOf(cell.getStringCellValue());
                value = value.replace(" ", "");
                value = value.replace("\n", "");
                value = value.replace("\t", "");
                obj = value;
                break;
            default:
                break;
        }
        return  obj;
    }


    public static void main(String[] args) throws Exception {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\hadoop\\Documents\\test.xls"));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        CellAddress cellAddress = new CellAddress(0,0);
        //Cell cell = ;
        System.out.println(cellAddress);
        System.out.println(sheet.getCellComment(cellAddress));

//        Integer data = new Integer((int) cell.getNumericCellValue());
//
//        System.out.println(data);


    }
}
