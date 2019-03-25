package com.gree.cn;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) throws Exception {
        // 打开指定位置的Excel文件
        String fileLocation = "C:\\Users\\hadoop\\Documents\\test.xls";
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        // 打开Excel中的第一个Sheet
        Sheet sheet = workbook.getSheetAt(0);

        CellAddress cellAddress = new CellAddress(0,0);
        System.out.println(sheet.getCellComment(cellAddress));
        // 读取Sheet中的数据
        Map<Integer, List<Object>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) { // 行
            data.put(i, new ArrayList<Object>());
            for (Cell cell : row) { // 单元格
                switch(cell.getCellType()) {
                    case STRING:
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if(DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue());
                        } else {
                            data.get(i).add(cell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula());
                        break;
                    case BLANK:
                        data.get(i).add("");
                        break;
                  }
                }
//            System.out.println(data.get(1.0));
            }
            i++;


    }

    }
