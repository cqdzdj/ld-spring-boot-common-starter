package com.lyldj.springboot.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @author: duanjian
 * @date: 17-11-21 下午6:02
 * @description: poi操作Excel
 */
public class PoiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoiUtils.class);
    
    /**
     * 默认的开始读取的行位置为第一行（索引值为0）
     */
    private static final int READ_START_POS = 0;

    /**
     * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
     */
    private static final  int READ_END_POS = 0;

    /**
     * 默认Excel内容的开始比较列位置为第一列（索引值为0）
     */
    private static final int COMPARE_POS = 0;

    /**
     * 默认多文件合并的时需要做内容比较（相同的内容不重复出现）
     */
    private static final boolean NEED_COMPARE = true;

    /**
     * 默认多文件合并的新文件遇到名称重复时，进行覆盖
     */
    private static final boolean NEED_OVERWRITE = true;

    /**
     * 默认只操作一个sheet
     */
    private static final boolean ONLY_ONE_SHEET = true;

    /**
     * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
     */
    private static final int SELECTED_SHEET = 0;

    /**
     * 默认从第一个sheet开始读取（索引值为0）
     */
    private static final int READ_START_SHEET = 0;

    /**
     * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
     */
    private static final int READ_END_SHEET = 0;

    /**
     * 默认打印各种信息
     */
    private static final boolean PRINT_MSG = true;

    /**
     * office 2003 扩展名
     */
    private static final String EXTENSION_XLS = "xls";
    /**
     * office 2007 及其以上版本扩展名
     */
    private static final String EXTENSION_XLSX = "xlsx";

    /**
     * Excel文件路径
     */
    private String excelPath = "data.xlsx";

    /**
     * 设定开始读取的位置，默认为0
     */
    private int startReadPos = READ_START_POS;

    /**
     * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
     */
    private int endReadPos = READ_END_POS;

    /**
     * 设定开始比较的列位置，默认为0
     */
    private int comparePos = COMPARE_POS;

    /**
     * 设定汇总的文件是否需要替换，默认为true
     */
    private boolean isOverWrite = NEED_OVERWRITE;

    /**
     * 设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)
     */
    private boolean isNeedCompare = NEED_COMPARE;

    /**
     * 设定是否只操作第一个sheet
     */
    private boolean onlyReadOneSheet = ONLY_ONE_SHEET;

    /**
     * 设定操作的sheet在索引值
     */
    private int selectedSheetIdx = SELECTED_SHEET;

    /**
     * 设定操作的sheet的名称
     */
    private String selectedSheetName = "";

    /**
     * 设定开始读取的sheet，默认为0
     */
    private int startSheetIdx = READ_START_SHEET;

    /**
     * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行
     */
    private int endSheetIdx = READ_END_SHEET;

    /**
     * 设定是否打印消息
     */
    private boolean printMsg = PRINT_MSG;

    public PoiUtils() {

    }

    public PoiUtils(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * 还原设定（其实是重新new一个新的对象并返回）
     * @return
     */
    public PoiUtils restoreSettings() {
        return new PoiUtils(this.excelPath);
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     * @throws IOException
     */
    public List<Row> readExcel() throws IOException {
        return readExcel(this.excelPath);
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     * @param excelPath
     * @throws IOException
     */
    public List<Row> readExcel(String excelPath) throws IOException {
        if (StringUtils.isBlank(excelPath)) {
            throw new IOException("文件路径不能为空！");
        } else {
            File file = new File(excelPath);
            if (!file.exists()) {
                throw new IOException("文件不存在！excelPath:" + excelPath);
            }
        }
        //获取扩展名
        String extension = FileUtils.getExtension(excelPath);
        try {
            if (EXTENSION_XLS.equals(extension)) {
                return readExcelXls(excelPath);
            } else if (EXTENSION_XLSX.equals(extension)) {
                return readExcelXlsx(excelPath);
            } else {
                return tryToReadExcel(excelPath);
            }
        } catch (IOException e) {
            LOGGER.error("读取失败！excelPath:" + excelPath);
            throw e;
        }
    }

    /**
     * 尝试读取Excel
     * @param excelPath
     * @return
     * @throws IOException
     */
    private List<Row> tryToReadExcel(String excelPath) throws IOException {
        LOGGER.info("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
        try {
            return readExcelXls(excelPath);
        } catch (IOException e1) {
            LOGGER.info("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
            try {
                return readExcelXlsx(excelPath);
            } catch (IOException e2) {
                LOGGER.error("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                throw e2;
            }
        }
    }

    /**
     * 自动根据文件扩展名，调用对应的写入方法
     * @param rowList
     * @throws IOException
     */
    public void writeExcel(List<Row> rowList) throws IOException {
        writeExcel(rowList, excelPath);
    }

    /**
     * 自动根据文件扩展名，调用对应的写入方法
     * @param rowList
     * @param excelPath
     * @throws IOException
     */
    public void writeExcel(List<Row> rowList, String excelPath) throws IOException {
        if ("".equals(excelPath)) {
            throw new IOException("文件路径不能为空！");
        }

        String extension = FileUtils.getExtension(excelPath);
        try {
            if (EXTENSION_XLS.equals(extension)) {
                writeExcelXls(rowList, excelPath);
            } else if (EXTENSION_XLSX.equals(extension)) {
                writeExcelXlsx(rowList, excelPath);
            } else {
                tryToWriteExcel(rowList, excelPath);
            }
        } catch (IOException e) {
            LOGGER.error("写入失败！");
            throw e;
        }
    }

    private void tryToWriteExcel(List<Row> rowList, String excelPath) throws IOException {
        LOGGER.info("您要操作的文件没有扩展名，正在尝试以xls方式写入...");
        try {
            writeExcelXls(rowList, excelPath);
        } catch (IOException e1) {
            LOGGER.info("尝试以xls方式写入，结果失败！，正在尝试以xlsx方式读取...");
            try {
                writeExcelXlsx(rowList, excelPath);
            } catch (IOException e2) {
                LOGGER.error("尝试以xls方式写入，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                throw e2;
            }
        }
    }

    /**
     * 修改Excel（97-03版，xls格式）
     * @param rowList
     * @param distXlsPath
     * @throws IOException
     */
    public void writeExcelXls(List<Row> rowList, String distXlsPath) throws IOException {
        writeExcelXls(rowList, excelPath, distXlsPath);
    }

    /**
     * 修改Excel（97-03版，xls格式）
     * @param rowList
     * @param excelPath
     * @param distXlsPath
     * @throws IOException
     */
    public void writeExcelXls(List<Row> rowList, String excelPath, String distXlsPath) throws IOException {
        // 判断文件路径是否为空
        if (StringUtils.isBlank(distXlsPath )) {
            LOGGER.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断文件路径是否为空
        if (StringUtils.isBlank(excelPath)) {
            LOGGER.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断列表是否有数据，如果没有数据，则返回
        if (CollectionUtils.isEmpty(rowList)) {
            LOGGER.info("文档为空");
            return;
        }
        InputStream inputStream = null;
        try {
            // 判断文件是否存在
            inputStream = getInputStream(excelPath, distXlsPath);
            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            // 将rowList的内容写到Excel中
            writeExcel(wb, rowList, distXlsPath);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if(Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    /**
     * 修改Excel（97-03版，xls格式）
     * @param rowList
     * @param distXlsxPath
     * @throws IOException
     */
    public void writeExcelXlsx(List<Row> rowList, String distXlsxPath) throws IOException {
        writeExcelXlsx(rowList, excelPath, distXlsxPath);
    }

    /**
     * 修改Excel（2007版，xlsx格式）
     * @param rowList
     * @param excelPath
     * @param distXlsxPath
     * @throws IOException
     */
    public void writeExcelXlsx(List<Row> rowList, String excelPath, String distXlsxPath) throws IOException {

        // 判断文件路径是否为空
        if (StringUtils.isBlank(distXlsxPath)) {
            LOGGER.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        // 判断文件路径是否为空
        if (StringUtils.isBlank(excelPath)) {
            LOGGER.info("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }

        // 判断列表是否有数据，如果没有数据，则返回
        if (CollectionUtils.isEmpty(rowList)) {
            LOGGER.info("文档为空");
            return;
        }

        InputStream inputStream = null;
        try {
            // 判断文件是否存在
            inputStream = getInputStream(excelPath, distXlsxPath);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            // 将rowList的内容添加到Excel中
            writeExcel(wb, rowList, distXlsxPath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if(Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    private InputStream getInputStream(String excelPath, String distXlsxPath) throws FileNotFoundException {
        File file = new File(distXlsxPath);
        if (file.exists()) {
            // 如果复写，则删除后
            if (isOverWrite) {
                file.delete();
                return new FileInputStream(excelPath);
            } else {
                // 如果文件存在，则读取Excel
                return new FileInputStream(file);
            }
        } else {
            return new FileInputStream(excelPath);
        }
    }

    /**
     * 读取Excel 2007版，xlsx格式
     * @return
     * @throws IOException
     */
    public List<Row> readExcelXlsx() throws IOException {
        return readExcelXlsx(excelPath);
    }

    /**
     * 读取Excel 2007版，xlsx格式
     * @return
     * @throws Exception
     */
    public List<Row> readExcelXlsx(String xlsPath) throws IOException {
        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        List<Row> rowList = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 去读Excel
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            // 读取Excel 2007版，xlsx格式
            rowList = readExcel(wb);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if(Objects.nonNull(fis)) {
                fis.close();
            }
        }
        return rowList;
    }

    /***
     * 读取Excel(97-03版，xls格式)
     * @throws IOException
     */
    public List<Row> readExcelXls() throws IOException {
        return readExcelXls(excelPath);
    }

    /***
     * 读取Excel(97-03版，xls格式)
     * @throws Exception
     */
    public List<Row> readExcelXls(String xlsPath) throws IOException {

        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        List<Row> rowList = new ArrayList<Row>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 读取Excel
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            // 读取Excel 97-03版，xls格式
            rowList = readExcel(wb);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if(Objects.nonNull(fis)) {
                fis.close();
            }
        }
        return rowList;
    }

    /***
     * 读取单元格的值
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 通用读取Excel
     * @param wb
     * @return
     */
    private List<Row> readExcel(Workbook wb) {
        List<Row> rowList = new ArrayList<>();
        int sheetCount = 1;//需要操作的sheet数量
        Sheet sheet = null;
        if (onlyReadOneSheet) {
            // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
            sheet = "".equals(selectedSheetName) ? wb.getSheetAt(selectedSheetIdx) : wb.getSheet(selectedSheetName);
        } else {
            //获取可以操作的总数量
            sheetCount = wb.getNumberOfSheets();
        }

        // 获取sheet数目
        for (int t = startSheetIdx; t < sheetCount + endSheetIdx; t++) {
            // 获取设定操作的sheet
            if (!onlyReadOneSheet) {
                sheet = wb.getSheetAt(t);
            }
            //获取最后行号
            int lastRowNum = sheet.getLastRowNum();
            //如果>0，表示有数据
            if (lastRowNum > 0) {
                LOGGER.info("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
            }

            Row row = null;
            // 循环读取
            for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                    LOGGER.info("第" + (i + 1) + "行：", false);
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = getCellValue(row.getCell(j));
                        if (!value.equals("")) {
                            LOGGER.info(value + " | ", false);
                        }
                    }
                }
            }
        }
        return rowList;
    }

    /**
     * 修改Excel，并另存为
     * @param wb
     * @param rowList
     * @param xlsPath
     */
    private void writeExcel(Workbook wb, List<Row> rowList, String xlsPath) {

        if (wb == null) {
            LOGGER.info("操作文档不能为空！");
            return;
        }

        Sheet sheet = wb.getSheetAt(0);// 修改第一个sheet中的值

        // 如果每次重写，那么则从开始读取的位置写，否则果获取源文件最新的行。
        int lastRowNum = isOverWrite ? startReadPos : sheet.getLastRowNum() + 1;
        int t = 0;//记录最新添加的行数
        LOGGER.info("要添加的数据总条数为：" + rowList.size());
        for (Row row : rowList) {
            if (row == null) {
                continue;
            }
            // 判断是否已经存在该数据
            int pos = findInExcel(sheet, row);

            Row r = null;// 如果数据行已经存在，则获取后重写，否则自动创建新行。
            if (pos >= 0) {
                sheet.removeRow(sheet.getRow(pos));
                r = sheet.createRow(pos);
            } else {
                r = sheet.createRow(lastRowNum + t++);
            }

            //用于设定单元格样式
            CellStyle newstyle = wb.createCellStyle();

            //循环为新行创建单元格
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = r.createCell(i);// 获取数据类型
                cell.setCellValue(getCellValue(row.getCell(i)));// 复制单元格的值到新的单元格
                // cell.setCellStyle(row.getCell(i).getCellStyle());//出错
                if (row.getCell(i) == null) {
                    continue;
                }
                copyCellStyle(row.getCell(i).getCellStyle(), newstyle); // 获取原来的单元格样式
                cell.setCellStyle(newstyle);// 设置样式
                // sheet.autoSizeColumn(i);//自动跳转列宽度
            }
        }
        LOGGER.info("其中检测到重复条数为:" + (rowList.size() - t) + " ，追加条数为：" + t);

        // 统一设定合并单元格
        setMergedRegion(sheet);

        try {
            // 重新将数据写入Excel中
            FileOutputStream outputStream = new FileOutputStream(xlsPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOGGER.error("写入Excel时发生错误！ ", e);

        }
    }

    /**
     * 查找某行数据是否在Excel表中存在，返回行数。
     * @param sheet
     * @param row
     * @return
     */
    private int findInExcel(Sheet sheet, Row row) {
        int pos = -1;

        try {
            // 如果覆写目标文件，或者不需要比较，则直接返回  
            if (isOverWrite || !isNeedCompare) {
                return pos;
            }
            for (int i = startReadPos; i <= sheet.getLastRowNum() + endReadPos; i++) {
                Row r = sheet.getRow(i);
                if (r != null && row != null) {
                    String v1 = getCellValue(r.getCell(comparePos));
                    String v2 = getCellValue(row.getCell(comparePos));
                    if (v1.equals(v2)) {
                        pos = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return pos;
    }

    /**
     * 复制一个单元格样式到目的单元格样式
     * @param fromStyle
     * @param toStyle
     */
    public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        // 边框和边框颜色  
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        // 背景和前景  
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        // 数据格式  
        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());

    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @return
     */
    public void setMergedRegion(Sheet sheet) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            // 获取合并单元格位置  
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            if (startReadPos - 1 > firstRow) {// 如果第一个合并单元格格式在正式数据的上面，则跳过。  
                continue;
            }
            int lastRow = ca.getLastRow();
            int mergeRows = lastRow - firstRow;// 合并的行数  
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            // 根据合并的单元格位置和大小，调整所有的数据行格式，  
            for (int j = lastRow + 1; j <= sheet.getLastRowNum(); j++) {
                // 设定合并单元格  
                sheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
                j = j + mergeRows;// 跳过已合并的行  
            }

        }
    }

    public String getExcelPath() {
        return this.excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public boolean isNeedCompare() {
        return isNeedCompare;
    }

    public void setNeedCompare(boolean isNeedCompare) {
        this.isNeedCompare = isNeedCompare;
    }

    public int getComparePos() {
        return comparePos;
    }

    public void setComparePos(int comparePos) {
        this.comparePos = comparePos;
    }

    public int getStartReadPos() {
        return startReadPos;
    }

    public void setStartReadPos(int startReadPos) {
        this.startReadPos = startReadPos;
    }

    public int getEndReadPos() {
        return endReadPos;
    }

    public void setEndReadPos(int endReadPos) {
        this.endReadPos = endReadPos;
    }

    public boolean isOverWrite() {
        return isOverWrite;
    }

    public void setOverWrite(boolean isOverWrite) {
        this.isOverWrite = isOverWrite;
    }

    public boolean isOnlyReadOneSheet() {
        return onlyReadOneSheet;
    }

    public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {
        this.onlyReadOneSheet = onlyReadOneSheet;
    }

    public int getSelectedSheetIdx() {
        return selectedSheetIdx;
    }

    public void setSelectedSheetIdx(int selectedSheetIdx) {
        this.selectedSheetIdx = selectedSheetIdx;
    }

    public String getSelectedSheetName() {
        return selectedSheetName;
    }

    public void setSelectedSheetName(String selectedSheetName) {
        this.selectedSheetName = selectedSheetName;
    }

    public int getStartSheetIdx() {
        return startSheetIdx;
    }

    public void setStartSheetIdx(int startSheetIdx) {
        this.startSheetIdx = startSheetIdx;
    }

    public int getEndSheetIdx() {
        return endSheetIdx;
    }

    public void setEndSheetIdx(int endSheetIdx) {
        this.endSheetIdx = endSheetIdx;
    }

    public boolean isPrintMsg() {
        return printMsg;
    }

    public void setPrintMsg(boolean printMsg) {
        this.printMsg = printMsg;
    }
}
