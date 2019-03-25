package com.gree.cn;
import jdbc.JDBCHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class PoiTest {

    public static void main(String[] args) {

        /**
         * 首先获取到某个路径，然后通过文件夹API读取文件夹的文件数已经获取文件的绝对路径
         * 接着讲获取的文件名存放到一个一个List<String>中，这样就完美的获取到了路径下的所有文件了
         */
        String path = "C:\\Users\\hadoop\\Documents\\test";
        List<String> list = new ArrayList<>();
        File file1 = new File(path);
        if (file1.isDirectory()) {
            String[] filelist = file1.list();
            for (int i = 0; i < filelist.length; i++) {
                list.add(path + "\\" + filelist[i]);
            }
        }
        /**
         * 通过之前一步获取的文件集合，以及POI工具类来解析excel
         * 获得我们想要的数据单元,然后将其存放到List<Object></>类中
         * name -> workbook -> sheet -> cell
         */
        List<Object[]> paramlist = new ArrayList<Object[]>();
        for ( int i= 0; i<list.size();i++) {
            File file = new File(list.get(i));
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


            paramlist.add(new Object[]{dmdq, xmbj, devdt});

        }
        /**
         * 然后通过jdbc组件 连接本地数据库，插入数据到MySQL表中
         */
        String sql = "insert ignore into product(dmdq,xmbj,devdt) value(?,?,?) ";
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeBatch(sql, paramlist);
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