package com.netease.lowcode.pdf.extension.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.pdf.extension.PdfGenerator;
import com.netease.lowcode.pdf.extension.structures.BaseResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TemplateUtils {


    // 是否设置logo 放在参数里
    public static String transfer() {
        // 读取excel解析出样式，然后输出模板json，传入pdfV2
        // 包括解析字体颜色，大小，映射出表格，文本等。

        // 直接将excel按照单元格解析，pdf中也创建一个大表格，通过映射单元格的边框 实现一一对应。
        // 解析excel不再出现段落的概念，全都是单元格cell

        try {
            // TODO:识别xls与xlsx分别处理
            String fileName = "C:\\Users\\fankehu\\Desktop\\新建 XLS 工作表.xlsx";
            InputStream inputStream = new FileInputStream(fileName);

            Workbook wb = null;
            if (fileName.endsWith(".xlsx")) {
                // 解析 *.xlsx
                wb = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                throw new RuntimeException("不支持");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileName","test22.pdf");
            // 字体设置
            jsonObject.put("font",new HashMap<String,String>(){{
                put("fontProgram","STSong-Light");
                put("encoding","UniGB-UCS2-H");
            }});
            // 纸张大小A4
            jsonObject.put("pageSize","A4");
            // 纸张方向
            jsonObject.put("rotate",true);
            JSONArray nodes = new JSONArray();
            jsonObject.put("nodes",nodes);

            // 读取第0个sheet
            Sheet sheet0 = wb.getSheetAt(0);
            if (Objects.isNull(sheet0)) {
                return null;
            }

            // 获取合并区域
            List<CellRangeAddress> mergedRegions = sheet0.getMergedRegions();
            if(CollectionUtils.isNotEmpty(mergedRegions)){
                for (CellRangeAddress mergedRegion : mergedRegions) {
                    int firstRow = mergedRegion.getFirstRow();
                    int lastRow = mergedRegion.getLastRow();
                    int firstColumn = mergedRegion.getFirstColumn();
                    int lastColumn = mergedRegion.getLastColumn();
                }
            }

            JSONObject table = new JSONObject();
            table.put("type","Table");
            table.put("width",100);

            JSONArray cells = new JSONArray();
            table.put("cells", cells);
            nodes.add(table);

            // 记录整个sheet列宽度
            List<Integer> sheetColWidthList = new ArrayList<>();
            // 暂存单元格
            List<List<JSONObject>> tmpCells = new ArrayList<>();


            // 遍历sheet行
            for (int i = 0; i < sheet0.getLastRowNum(); i++) {
                Row row = sheet0.getRow(i);
                if(Objects.isNull(row)){
                    continue;
                }

                // 暂存该行单元格
                List<JSONObject> curRowTmpCells = new ArrayList<>();
                tmpCells.add(curRowTmpCells);

                List<Integer> currentRowColWidths = new ArrayList<>();
                // 遍历列，注意模板不要超过A4的宽度
                for (int j = 0; j < row.getLastCellNum(); j++) {

                    JSONObject jsonCell = new JSONObject();
                    JSONArray elements = new JSONArray();
                    JSONObject paragraph = new JSONObject();
                    paragraph.put("type","Paragraph");
                    elements.add(paragraph);
                    jsonCell.put("elements", elements);

                    curRowTmpCells.add(jsonCell);


                    // TODO: 判断是否位于合并区域


                    // 获取单元格
                    Cell cell = row.getCell(j);

                    if (Objects.isNull(cell)) {
                        paragraph.put("text", "null");
                        currentRowColWidths.add(0);
                        continue;
                    }

                    currentRowColWidths.add(sheet0.getColumnWidth(j));

                    // 单元格字体
                    Font font = wb.getFontAt(cell.getCellStyle().getFontIndexAsInt());
                    // 字体颜色
                    short color = font.getColor();
                    // 字体大小
                    short fontSize = font.getFontHeightInPoints();
                    // 字体是否加粗
                    boolean bold = font.getBold();
                    // 下划线
                    byte underline = font.getUnderline();


                    // 单元格类型
                    CellType cellType = cell.getCellType();
                    if (CellType.STRING.equals(cellType)) {
                        paragraph.put("text", cell.getStringCellValue());
                    } else if (CellType.BLANK.equals(cellType)) {
                        paragraph.put("text", "blank");
                    }


                    // 判断水平居中
                    HorizontalAlignment alignment = cell.getCellStyle().getAlignment();
                    // 判断垂直居中
                    VerticalAlignment verticalAlignment = cell.getCellStyle().getVerticalAlignment();

                    // 表格底部边框
                    BorderStyle borderBottom = cell.getCellStyle().getBorderBottom();
                    if (BorderStyle.THIN.equals(borderBottom)) {
                        JSONObject value = new JSONObject();
                        value.put("width", 1);
                        jsonCell.put("borderBottom", value);
                    }
                    BorderStyle borderTop = cell.getCellStyle().getBorderTop();
                    if (BorderStyle.THIN.equals(borderTop)) {
                        JSONObject value = new JSONObject();
                        value.put("width", 1);
                        jsonCell.put("borderTop", value);
                    }
                    BorderStyle borderLeft = cell.getCellStyle().getBorderLeft();
                    if (BorderStyle.THIN.equals(borderLeft)) {
                        JSONObject value = new JSONObject();
                        value.put("width", 1);
                        jsonCell.put("borderLeft", value);
                    }
                    BorderStyle borderRight = cell.getCellStyle().getBorderRight();
                    if (BorderStyle.THIN.equals(borderRight)) {
                        JSONObject value = new JSONObject();
                        value.put("width", 1);
                        jsonCell.put("borderRight", value);
                    }

                }

                // 设置宽度
                if (currentRowColWidths.size() > sheetColWidthList.size()) {
                    for (int j = 0; j < sheetColWidthList.size(); j++) {
                        if (sheetColWidthList.get(j) == 0) {
                            sheetColWidthList.set(j, currentRowColWidths.get(j));
                        }
                    }
                    int size = sheetColWidthList.size();
                    for (int j = size; j < currentRowColWidths.size(); j++) {
                        sheetColWidthList.add(currentRowColWidths.get(j));
                    }
                } else {
                    for (int j = 0; j < currentRowColWidths.size(); j++) {
                        if (sheetColWidthList.get(j) == 0) {
                            sheetColWidthList.set(j, currentRowColWidths.get(j));
                        }
                    }
                }
            }
            // 单元格末尾对齐填充
            int maxSize = tmpCells.stream().mapToInt(List::size).max().orElse(0);
            for (List<JSONObject> tmpRow : tmpCells) {
                if (tmpRow.size() < maxSize) {
                    int diff = maxSize - tmpRow.size();
                    for (int i = 0; i < diff; i++) {
                        JSONObject jsonCell = new JSONObject();
                        JSONArray elements = new JSONArray();
                        JSONObject paragraph = new JSONObject();
                        paragraph.put("type", "Paragraph");
                        paragraph.put("text","对齐");
                        elements.add(paragraph);
                        jsonCell.put("elements", elements);
                        tmpRow.add(jsonCell);
                    }
                }
            }
            for (int i = 0; i < tmpCells.size(); i++) {
                List<JSONObject> list = tmpCells.get(i);
                for (JSONObject object : list) {
                    // 去除单元格默认边框
                    object.put("noBorder", true);
                }
                cells.addAll(list);
            }

            // 设置表格列数
            table.put("columnSize",maxSize);
            // TODO 通过计算获得
            // table.put("chunkSize",2);


            // 从全局设置cell宽度
            int totalColWidth = sheetColWidthList.stream().mapToInt(Integer::intValue).sum();
            for (int i = 0; i < cells.size(); i++) {
                JSONObject jsonCell = cells.getJSONObject(i);
                float value = (sheetColWidthList.get(i % sheetColWidthList.size()) / (float) totalColWidth) * 100;
                jsonCell.put("width", Math.round(value));
            }

            BaseResponse response = PdfGenerator.createPDFV2ByStr("{\n" +
                    "    \"name\":\"测试名字\"\n" +
                    "}", jsonObject.toJSONString());
            if (response.getSuccess()) {
                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        TemplateUtils.transfer();
    }
}
