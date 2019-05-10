package com.ssm.demo.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/5
 */
public class ExcelUtil {
    @Value("${config.attachFolder}")
    private static String attachFolder;

    static CellStyle style = null, style2 = null, style3 = null, styleMainTitle = null, styleSubTitle = null;
    static Font font = null, font2 = null;

    public static void buildExcel(OutputStream outStream, String mainTitle, String[] titles, List<String[]> contents, String sheetName) {
        buildExcel(outStream,
                mainTitle, null, titles, contents, sheetName, null,
                null, null, null, null, null);
    }

    public static void buildExcel(OutputStream outStream, String mainTitle, String[] titles, List<String[]> contents, String sheetName, List<MergeRegion> mergeRegions) {
        buildExcel(outStream,
                mainTitle, null, titles, contents, sheetName, mergeRegions,
                null, null, null, null, null);
    }

    public static void buildExcel(OutputStream outStream,
                                  String mainTitle, String[] titles, List<String[]> contents, String sheetName, List<MergeRegion> mergeRegions,
                                  String mainTitle2, String[] titles2, List<String[]> contents2, String sheetName2) {
        buildExcel(outStream,
                mainTitle, null, titles, contents, sheetName, mergeRegions,
                mainTitle2, null, titles2, contents2, sheetName2);
    }

    /**
     * 生成excel
     * @param outStream
     * @param mainTitle
     * @param subTitles
     * @param titles
     * @param contents
     * @param sheetName
     * @param mergeRegions
     * @param mainTitle2
     * @param subTitles2
     * @param titles2
     * @param contents2
     * @param sheetName2
     */
    public static void buildExcel(OutputStream outStream,
            String mainTitle, List<Map<Integer, String>> subTitles, String[] titles, List<String[]> contents, String sheetName,
            List<MergeRegion> mergeRegions, String mainTitle2, List<Map<Integer, String>> subTitles2, String[] titles2,
            List<String[]> contents2, String sheetName2) {
        try {
            //创建工作簿
            SXSSFWorkbook wb = new SXSSFWorkbook(25);

            /** 头部样式 */
            style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(false);

            //生成一个字体
            font = wb.createFont();
            font.setFontHeightInPoints((short)10);
            font.setBold(true);
            style.setFont(font);

            /** 正文样式 */
            style2 = wb.createCellStyle();
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            style2.setWrapText(true);
            //生成一个字体
            font2 = wb.createFont();
            font2.setFontHeightInPoints((short)10);
            style2.setFont(font2);
            //设置样式
            style3 = wb.createCellStyle();
            style3.setBorderBottom(BorderStyle.THIN);
            style3.setBorderLeft(BorderStyle.THIN);
            style3.setBorderRight(BorderStyle.THIN);
            style3.setBorderTop(BorderStyle.THIN);
            style3.setWrapText(true);
            style3.setFont(font2);
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

            /** 主标题样式 */
            styleMainTitle = wb.createCellStyle();
            styleMainTitle.setBorderBottom(BorderStyle.NONE);
            styleMainTitle.setBorderLeft(BorderStyle.NONE);
            styleMainTitle.setBorderRight(BorderStyle.NONE);
            styleMainTitle.setBorderTop(BorderStyle.NONE);
            styleMainTitle.setVerticalAlignment(VerticalAlignment.CENTER);
            styleMainTitle.setAlignment(HorizontalAlignment.CENTER);
            styleMainTitle.setWrapText(false);
            styleMainTitle.setFont(font);

            /** 副标题样式 */
            styleSubTitle = wb.createCellStyle();
            styleMainTitle.setBorderBottom(BorderStyle.NONE);
            styleMainTitle.setBorderLeft(BorderStyle.NONE);
            styleMainTitle.setBorderRight(BorderStyle.NONE);
            styleMainTitle.setBorderTop(BorderStyle.NONE);
            styleSubTitle.setVerticalAlignment(VerticalAlignment.CENTER);
            styleSubTitle.setAlignment(HorizontalAlignment.LEFT);
            styleSubTitle.setWrapText(false);
            styleSubTitle.setFont(font2);

            sheetName = StringUtils.isEmpty(sheetName) ? "Sheet1" : sheetName;
            buildSheet(wb, mainTitle, subTitles, titles, contents, sheetName, mergeRegions);

            if(titles2 != null) {
                sheetName2 = StringUtils.isEmpty(sheetName2) ? "Sheet2" : sheetName2;
                buildSheet(wb, mainTitle2, subTitles2, titles2, contents2, sheetName2, null);
            }

            wb.write(outStream);
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void buildSheet(SXSSFWorkbook wb, String mainTitle, List<Map<Integer, String>> subTitles, String[] titles,
               List<String[]> contents, String sheetName, List<MergeRegion> mergeRegions) {
        //创建工作表
        Sheet sheet = wb.createSheet(sheetName);
        int beginRow = 0;

        if(mainTitle != null && !"".equals(mainTitle)) {
            sheet.addMergedRegion(new CellRangeAddress(beginRow, 0, 0, titles.length-1));
            Row row = sheet.createRow(beginRow);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styleMainTitle);
            cell.setCellValue(mainTitle);
            beginRow++;
        }

        //副标题栏
        if(subTitles != null && subTitles.size() > 0) {
            for (Map<Integer, String> item : subTitles) {
                Row row = sheet.createRow(beginRow);
                for (Integer key : item.keySet()) {
                    Cell cell = row.createCell(key);
                    cell.setCellStyle(styleSubTitle);
                    sheet.setColumnWidth(key, 2200);
                    cell.setCellValue(item.get(key));
                }
                beginRow++;
            }
        }

        //创建标题
        if(titles != null && titles.length > 0) {
            Row row = sheet.createRow(beginRow);
            for(int i = 0; i < titles.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                sheet.setColumnWidth(i, 2200);
                cell.setCellValue(titles[i]);
            }
            beginRow++;
        }

        //合并单元格
        if(mergeRegions != null) {
            for (MergeRegion mergeRegion : mergeRegions) {
                if(mergeRegion.getFirstRow() >= mergeRegion.getLastRow() && mergeRegion.getFirstCol() >= mergeRegion.getLastCol()) {
                    continue;
                }

                sheet.addMergedRegion(new CellRangeAddress(mergeRegion.getFirstRow(), mergeRegion.getLastRow()
                        , mergeRegion.getFirstCol(), mergeRegion.getLastCol()));
            }
        }

        //创建Excel正文数据
        if(contents != null && !contents.isEmpty()) {
            for (int i = 0; i< contents.size(); i++) {
                String[] rowContent = contents.get(i);
                Row row_value = sheet.createRow(beginRow);
                for(int j = 0; titles != null && j< titles.length; j++) {
                    Cell cell_value = row_value.createCell(j);
                    cell_value.setCellStyle(i % 2 == 0 ? style2 : style3);
                    cell_value.setCellValue(rowContent[j]);
                }
                beginRow++;
            }
        }
    }

//    /**
//     * 从指定sheet导出数据
//     */
//    public static List<Map<Integer, String>> extractDatas(String tplFileName, int sheetNo, int startRow) throws EncryptedDocumentException, InvalidFormatException, IOException {
//        InputStream is = new FileInputStream(attachFolder + tplFileName);
//
//        return extractDatas(is, sheetNo, startRow);
//    }
//
//    /**
//     * 从指定sheet导出数据
//     */
//    public static List<Map<Integer, String>> extractDatas(InputStream is, int sheetNo, int startRow) throws EncryptedDocumentException, InvalidFormatException, IOException {
//        List<Map<Integer, String>> datas = new ArrayList<Map<Integer,String>>();
//        Map<Integer, String> data = null;
//        Row row = null;
//        Cell cell = null;
//
//        Workbook workbook = WorkbookFactory.create(is);
//        Sheet sheet = workbook.getSheetAt(sheetNo);
//        for(int i = startRow; i <= sheet.getLastRowNum(); i++) {
//            data = new HashMap<Integer, String>();
//            row = sheet.getRow(i);
//
//            if (row == null) {
//                break;
//            }
//
//            for(int j = 0; j < row.getLastCellNum(); j++) {
//                cell = CellUtil.getCell(row, j);
//
//                if (cell == null) {
//                    data.put(j, null);
//                    continue;
//                }
//
//                data.put(j, StringUtils.chomp(cell.toString(), ".0"));
//
//                if (data.get(j).matches("[0-9]{2}-.{2,3}-[0-9]{4}")) {
//                    data.put(j,  DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss"));
//                }
//
//                if (data.get(j).matches("^[0-9].[0-9]+E[0-9]+$")) {
//                    data.put(j, String.format("%.0f", cell.getNumericCellValue()));
//                }
//            }
//
//            datas.add(data);
//        }
//
//        return datas;
//    }

    /**
     * 合并单元格
     */
    public static class MergeRegion {
        private int firstRow;
        private int lastRow;
        private int firstCol;
        private int lastCol;

        public MergeRegion(int firstRow, int lastRow, int firstCol, int lastCol) {
            this.firstRow = firstRow;
            this.lastRow = lastRow;
            this.firstCol = firstCol;
            this.lastCol = lastCol;
        }

        public int getFirstRow() {
            return firstRow;
        }

        public void setFirstRow(int firstRow) {
            this.firstRow = firstRow;
        }

        public int getLastRow() {
            return lastRow;
        }

        public void setLastRow(int lastRow) {
            this.lastRow = lastRow;
        }

        public int getFirstCol() {
            return firstCol;
        }

        public void setFirstCol(int firstCol) {
            this.firstCol = firstCol;
        }

        public int getLastCol() {
            return lastCol;
        }

        public void setLastCol(int lastCol) {
            this.lastCol = lastCol;
        }
    }
}
