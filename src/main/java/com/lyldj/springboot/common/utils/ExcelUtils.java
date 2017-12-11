package com.lyldj.springboot.common.utils;

import com.lyldj.springboot.common.annotation.ExcelExport;
import com.lyldj.springboot.common.annotation.Exportable;
import com.lyldj.springboot.common.annotation.Parser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author: duanjian
 * @date: 17-11-21 上午10:31
 * @description: Excel导入导出工具类(只适用于简单表头)
 */
public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    /**
     * office 1997~2003 扩展名
     */
    private static final String EXTENSION_XLS = "xls";
    /**
     * office 2007 及其以上版本扩展名
     */
    private static final String EXTENSION_XLSX = "xlsx";
    /**
     * 默认的开始读取的行位置为第一行（索引值为0）
     */
    private static final int START_ROW = 0;

    private static final String DEFAULT_DATE_PATTERN = DateUtils.DATE_TIME_PATTERN;

    public static <T extends Exportable> void export(String excelPath, T data) {

    }

    public static <T extends Exportable> void export(String excelPath, List<T> datas, Class<T> clazz) throws Exception {
        if (StringUtils.isBlank(excelPath)) {
            throw new Exception("excelPath can't be blank！");
        }
        if (CollectionUtils.isEmpty(datas)) {
            throw new Exception("export datas can't be empty！");
        }
        List<Field> fieldList = getFieldsForExport(clazz);

        String extension = FileUtils.getExtension(excelPath);
        if (EXTENSION_XLS.equals(extension)) {
            exportExcelXls(excelPath, datas, fieldList);
        } else {
            exportExcelXlsx(excelPath, datas, fieldList);
        }

    }

    private static <T> void exportExcelXls(String excelPath, List<T> datas, List<Field> fields) {

    }

    private static <T> void exportExcelXlsx(String excelPath, List<T> datas, List<Field> fields) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        createHeaderRow(fields, workbook, sheet);

        for (int row = 0; row < datas.size(); row++) {
            Row bodyRow = sheet.createRow(sheet.getLastRowNum() + 1);
            T data = datas.get(row);
            for(int col = 0; col < fields.size(); col++) {
                Field field = fields.get(col);
                CellStyle style = getBodyCellStyle(workbook);
                Cell cell = bodyRow.createCell(col);
                cell.setCellStyle(style);
                cell.setCellValue(getValue(field, data));
            }

        }


    }
    
    private static <T> String getValue(Field field, T data) {
        try {
            ExcelExport excelExport = field.getAnnotation(ExcelExport.class);
            Object propertyValue = field.get(data);
            if(Objects.isNull(propertyValue)) {
                return StringUtils.isBlank(excelExport.nullValue()) ? "" : excelExport.nullValue();
            }
            if(data instanceof Date) {
                String pattern = excelExport.pattern();
                if(StringUtils.isBlank(pattern)) {
                    pattern = DEFAULT_DATE_PATTERN;
                }
            }
            Class<? extends Parser> clazz = excelExport.parser();
            return clazz.newInstance().parse(data);
        } catch (Exception e) {
            LOGGER.error("ExcelUtils.getValue occurs an error!", e);
        }
        return "";
    }

    private static void createHeaderRow(List<Field> fields, Workbook workbook, Sheet sheet) {
        Row headerRow = sheet.createRow(START_ROW);
        CellStyle headerStyle = getHeaderCellStyle(workbook);
        for (int i = 0; i < fields.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            String header = fields.get(i).getAnnotation(ExcelExport.class).header();
            cell.setCellValue(header);
        }
    }

    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        // 加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置背景色
        style.setFillForegroundColor(HSSFColor.LIME.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 让单元格居中
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

    private static CellStyle getBodyCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        // 设置字体大小
        font.setFontHeightInPoints((short) 12);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 让单元格居中
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

    private static <T extends Exportable> List<Field> getFieldsForExport(Class<T> clazz) throws Exception {
        List<Field> fieldList = new LinkedList<>();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if(Objects.nonNull(field.getAnnotation(ExcelExport.class))) {
                fieldList.add(field);
            }
        }
        if(CollectionUtils.isEmpty(fieldList)) {
            throw new Exception("unspecified export fields!");
        }
        fieldList.sort((o1, o2) -> o2.getAnnotation(ExcelExport.class).order() - o1.getAnnotation(ExcelExport.class).order());
        return fieldList;
    }
}