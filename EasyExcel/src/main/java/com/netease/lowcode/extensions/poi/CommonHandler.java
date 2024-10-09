package com.netease.lowcode.extensions.poi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.netease.lowcode.extensions.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

public class CommonHandler {

    public static void parseTitle(Class<?> clazz,SheetData sheetData) {
        // 解析表头数据：复杂表头：在label中 使用 主表头1-子表头2 会自动合并相同且相邻的主表头
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {

            // 字段名
            String name = declaredField.getName();
            Class<?> type = declaredField.getType();
            String title = "";


            // 获取注解表头
            Annotation[] annotations = declaredField.getAnnotations();
            if(Objects.nonNull(annotations)){
                for (Annotation annotation : annotations) {
                    String simpleName = annotation.annotationType().getSimpleName();
                    // 如果没有Label注解就不解析 表头样式，该列不导出到excel
                    if(StringUtils.equals("Label",simpleName)){
                        //
                        try {
                            String value = (String) annotation.annotationType().getMethod("value").invoke(annotation);
                            // 如果是以 @Style=json 开头就解析json内容作为样式
                            // 否则直接获取value作为表头

                            if(StringUtils.startsWith(value,"@Style=")) {
                                // 解析样式
                                String substring = value.substring(7);

                                CellStyle style = JsonUtil.fromJson(substring, CellStyle.class);
                                sheetData.putColStyleEntry(name, style);
                                sheetData.putColHeadIndexEntry(name, style.getIndex());
                                title = style.getTitle();
                            } else {
                                title = value;
                            }

                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                                 JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            sheetData.putColHeadEntry(name, title);
            sheetData.putColJavaTypeEntry(name, type);
        }
    }

    public static void addTitle(SheetData sheetData) {

        // todo: 目前仅支持解析一级表头，后续可通过style配置表头的parent节点，从而获取合并区域

        RowData rowData = new RowData();

        List<Boolean> indexFlag = new ArrayList<>(sheetData.getColHeadMap().size());
        for (int i = 0; i < sheetData.getColHeadMap().size(); i++) {
            indexFlag.add(true);
        }
        for (Integer value : sheetData.getColHeadIndexMap().values()) {
            // 可能超界

            indexFlag.set(value, false);
        }


        int tmpIndex = 0;
        for (Map.Entry<String, String> entry : sheetData.getColHeadMap().entrySet()) {
            Integer colHeadIndex = sheetData.getColHeadIndex(entry.getKey());
            if (Objects.nonNull(colHeadIndex) && colHeadIndex >= 0) {
                // 已经存在不重复添加
                continue;
            }

            while (tmpIndex < indexFlag.size()) {

                if (indexFlag.get(tmpIndex)) {
                    indexFlag.set(tmpIndex, false);
                    sheetData.putColHeadIndexEntry(entry.getKey(), tmpIndex++);
                    break;
                }

                tmpIndex++;
            }
        }


        // 如果在style中未指定列顺序，则指定默认顺序
        for (Map.Entry<String, String> entry : sheetData.getColHeadMap().entrySet()) {
            CellData cellData = new CellData();
            cellData.setIndex(sheetData.getColHeadIndex(entry.getKey()));
            cellData.setData(entry.getValue());
            rowData.addCell(cellData);
        }
        sheetData.addRow(rowData);
    }

    public static void addData(POIExcelCreateDTO request, SheetData sheetData) {
        try {
            JsonNode arrayNode = JsonUtil.fromJson(request.getJsonData());
            if (!arrayNode.isArray()) {
                throw new RuntimeException("不是数组");
            }
            for (int i = 0; i < arrayNode.size(); i++) {

                JsonNode node = arrayNode.get(i);
                RowData rowData = new RowData();
                sheetData.addRow(rowData);


                for (String colName : sheetData.getColNames()) {

                    // 默认不指定顺序，后续看情况支持通过cell索引指定列的顺序
                    CellData cellData = new CellData();
                    // 处理类型映射
                    // * NASL类型和Java类型匹配关系：
                    // * NASL：Boolean，  Java：java.lang.Boolean
                    // * NASL：Integer，  Java：java.lang.Long
                    // * NASL：String，   Java：java.lang.String
                    // * NASL：Time，     Java：java.time.LocalTime
                    // * NASL：Date，     Java：java.time.LocalDate
                    // * NASL：DateTime， Java：java.time.ZonedDateTime
                    // * NASL：Decimal，  Java：java.math.BigDecimal
                    // * NASL：List，     Java：java.util.List
                    // * NASL：Map，      Java：java.util.Map
                    if (Boolean.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asBoolean());
                    } else if (Long.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asDouble());
                    } else if (String.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (LocalTime.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (LocalDate.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (ZonedDateTime.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (BigDecimal.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (List.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else if (Map.class.equals(sheetData.getColJavaType(colName))) {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    } else {
                        JsonNode colValue = node.get(colName);
                        cellData.setData(colValue.asText());
                    }
                    // 设置列索引，与表头一致
                    cellData.setIndex(sheetData.getColHeadIndex(colName));
                    // 读取样式
                    cellData.setCellStyle(sheetData.getColStyle(colName));
                    rowData.addCell(cellData);
                }

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createXlsx(ExcelData excelData) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
    }

    public static void createXls(ExcelData excelData) {

        HSSFWorkbook wb = new HSSFWorkbook();
        for (SheetData sheetData : excelData.getSheetList()) {
            HSSFSheet sheet = wb.createSheet();

            // 设置合并区域
            for (MergedRegion mergedRegion : sheetData.getMergedRegionList()) {
                sheet.addMergedRegion(new CellRangeAddress(mergedRegion.getFirstRow(),
                        mergedRegion.getLastRow(), mergedRegion.getFirstCol(), mergedRegion.getLastCol()));
            }

            for (int i = 0; i < sheetData.getRowDataList().size(); i++) {

                // 创建一行数据
                HSSFRow row = sheet.createRow(i);
                RowData rowData = sheetData.getRow(i);

                for (int j = 0; j < rowData.getCellDataList().size(); j++) {

                    HSSFCell cell = row.createCell(j);
                    CellData cellData = rowData.getCell(j);

                    if (cellData.getData() instanceof String) {
                        cell.setCellValue((String) cellData.getData());
                    } else if (cellData.getData() instanceof LocalDate) {
                        cell.setCellValue((LocalDate) cellData.getData());
                    } else if (cellData.getData() instanceof Boolean) {
                        cell.setCellValue((Boolean) cellData.getData());
                    } else if (cellData.getData() instanceof Double) {
                        cell.setCellValue((Double) cellData.getData());
                    } else if (cellData.getData() instanceof Date) {
                        cell.setCellValue((Date) cellData.getData());
                    } else if (cellData.getData() instanceof LocalDateTime) {
                        cell.setCellValue((LocalDateTime) cellData.getData());
                    } else if (cellData.getData() instanceof Calendar) {
                        cell.setCellValue((Calendar) cellData.getData());
                    } else if (cellData.getData() instanceof RichTextString) {
                        cell.setCellValue((RichTextString) cellData.getData());
                    } else {
                        throw new RuntimeException("不支持的类型");
                    }

                    // 设置单元格样式
                    setCellStyle(cellData, cell, wb);

                }
            }
        }

        try {
            wb.write(Files.newOutputStream(Paths.get(excelData.getName())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setCellStyle(CellData cellData, HSSFCell cell, HSSFWorkbook wb) {
        CellStyle cellStyle = cellData.getCellStyle();
        if (Objects.isNull(cellStyle)) {
            return;
        }

        HSSFCellStyle hssfCellStyle = wb.createCellStyle();


        if(StringUtils.isNotBlank(cellStyle.getBackgroundCondition())){
            // 需要符合规范 GREEN<20:RED<BLACK 且必须是long
            if(cellData.getData() instanceof Double){
                String[] split = cellStyle.getBackgroundCondition().split("<");

                if ((Double) cellData.getData() < Double.valueOf(split[1].split(":")[0])) {
                    cellStyle.setBackground(split[0]);
                } else if ((Double) cellData.getData() > Double.valueOf(split[1].split(":")[0])) {
                    cellStyle.setBackground(split[2]);
                } else {
                    cellStyle.setBackground(split[1].split(":")[1]);
                }
                hssfCellStyle.setFillForegroundColor(cellStyle.getBackground());
                hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
        }
        else if (cellStyle.getBackground() > 0) {
            hssfCellStyle.setFillForegroundColor(cellStyle.getBackground());
            hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        cell.setCellStyle(hssfCellStyle);
    }

    private static void setCellValue() {

    }
}
