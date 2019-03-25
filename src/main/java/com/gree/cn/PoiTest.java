package com.gree.cn;

import jdbc.JDBCHelper;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Function;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.ReadFile;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoiTest {

    public static void main(String[] args) {
        String path = "C:\\Users\\hadoop\\Documents\\test";
        List<String> list = new ArrayList<>();
        File file1 = new File(path);
        if (file1.isDirectory()) {
            String[] filelist = file1.list();
            for (int i = 0; i < filelist.length; i++) {
                list.add(path + "\\" + filelist[i]);
            }
        }
        for ( int i= 0; i<list.size();i++) {
            File file = new File(list.get(i).toString());
            InputStream in = null;
            Workbook workbook = null;
            try{
                in = new FileInputStream(file);
                workbook = new XSSFWorkbook(in);
            }catch(Exception e){
              System.out.print(e.getMessage());
            }
            Sheet sheet = workbook.getSheetAt(0);
            String dmdq = getCellValue(sheet.getRow(5).getCell(4));
            String xmbj = getCellValue(sheet.getRow(8).getCell(4));
            String devdt = getCellValue(sheet.getRow(11).getCell(8));

            List<Object[]> paramlist = new ArrayList<Object[]>();
            paramlist.add(new Object[]{dmdq, xmbj, devdt});
            String sql = "insert ignore into product(dmdq,xmbj,devdt) value(?,?,?) ";
            JDBCHelper jdbcHelper = JDBCHelper.getInstance();
            jdbcHelper.executeBatch(sql, paramlist);
            System.out.println(dmdq + " " + xmbj + " " + devdt);

        }
    }

    //未处理公式
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//非线程安全
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    //处理公式
    public static String getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null || formulaEvaluator == null) {
            return null;
        }

        if (cell.getCellType() == CellType.FORMULA) {
            return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
        }
        return getCellValue(cell);
    }

}