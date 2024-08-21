package com.netease.lowcode.pdf.extension.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.pdf.extension.PdfGenerator;
import com.netease.lowcode.pdf.extension.structures.BaseResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TemplateUtils {


    // 是否设置logo 放在参数里
    public static String transfer(String jsonData) {
        // 读取excel解析出样式，然后输出模板json，传入pdfV2
        // 包括解析字体颜色，大小，映射出表格，文本等。

        // 直接将excel按照单元格解析，pdf中也创建一个大表格，通过映射单元格的边框 实现一一对应。
        // 解析excel不再出现段落的概念，全都是单元格cell

        try {
            // 解析参数
            JSONObject requestJsonData = JSONObject.parseObject(jsonData);

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
            jsonObject.put("fileName","test23.pdf");
            // 字体设置
            jsonObject.put("font",new HashMap<String,String>(){{
                put("fontProgram","STSong-Light");
                put("encoding","UniGB-UCS2-H");
            }});
            // 纸张大小A4
            jsonObject.put("pageSize","A4");
            // 纸张方向
            jsonObject.put("rotate",false);
            JSONArray nodes = new JSONArray();
            jsonObject.put("nodes",nodes);

            // 读取第0个sheet
            Sheet sheet0 = wb.getSheetAt(0);
            if (Objects.isNull(sheet0)) {
                return null;
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
            // 统计freemarker list语法
            List<String> freemarkerList = new ArrayList<>();


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
                        currentRowColWidths.add(0);
                        continue;
                    }

                    currentRowColWidths.add(sheet0.getColumnWidth(j));

                    // 单元格字体
                    Font font = wb.getFontAt(cell.getCellStyle().getFontIndexAsInt());
                    // 字体颜色
                    if (font instanceof XSSFFont) {
                        XSSFFont xssfFont = (XSSFFont) font;
                        XSSFColor xssfColor = xssfFont.getXSSFColor();
                        if(Objects.nonNull(xssfColor)) {
                            byte[] rgb = xssfColor.getRGB();
                            paragraph.put("rgb", new HashMap<String, Integer>() {
                                {
                                    put("red", (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0]);
                                    put("green", (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1]);
                                    put("blue", (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2]);
                                }
                            });
                        }
                    }
                    // 字体大小
                    short fontSize = font.getFontHeightInPoints();
                    paragraph.put("fontSize",fontSize);
                    // 字体是否加粗
                    boolean bold = font.getBold();
                    paragraph.put("bold", bold);
                    // 下划线
                    byte underline = font.getUnderline();


                    // 单元格类型
                    CellType cellType = cell.getCellType();
                    if (CellType.STRING.equals(cellType)) {
                        paragraph.put("text", cell.getStringCellValue());
                    }


                    // 判断水平居中
                    HorizontalAlignment alignment = cell.getCellStyle().getAlignment();
                    if (HorizontalAlignment.CENTER.equals(alignment)) {
                        jsonCell.put("textAlignment", "CENTER");
                    }
                    // 判断垂直居中
                    VerticalAlignment verticalAlignment = cell.getCellStyle().getVerticalAlignment();
                    if (VerticalAlignment.CENTER.equals(verticalAlignment)) {

                    }

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
                        elements.add(paragraph);
                        jsonCell.put("elements", elements);
                        tmpRow.add(jsonCell);
                    }
                }
            }

            // 处理合并区域
            List<CellRangeAddress> mergedRegions = sheet0.getMergedRegions();
            if(CollectionUtils.isNotEmpty(mergedRegions)){
                for (CellRangeAddress mergedRegion : mergedRegions) {
                    int firstRow = mergedRegion.getFirstRow();
                    int lastRow = mergedRegion.getLastRow();
                    int firstColumn = mergedRegion.getFirstColumn();
                    int lastColumn = mergedRegion.getLastColumn();

                    // 记录合并单元格
                    JSONObject mergeCell = tmpCells.get(firstRow).get(firstColumn);
                    mergeCell.put("rowspan", lastRow - firstRow + 1);
                    mergeCell.put("colspan", lastColumn - firstColumn + 1);

                    // 处理合并单元格边框,仅需处理右侧、底部
                    if (tmpCells.get(firstRow).get(lastColumn).containsKey("borderRight")) {
                        mergeCell.put("borderRight", tmpCells.get(firstRow).get(lastColumn).getJSONObject("borderRight"));
                    }
                    if (tmpCells.get(lastRow).get(lastColumn).containsKey("borderBottom")) {
                        mergeCell.put("borderBottom", tmpCells.get(firstRow).get(lastColumn).getJSONObject("borderBottom"));
                    }

                    // 处理合并区域第一行
                    List<JSONObject> first = tmpCells.get(firstRow);
                    List<JSONObject> newFirst = new ArrayList<>();
                    for (int i = 0; i < firstColumn; i++) {
                        newFirst.add(first.get(i));
                    }
                    newFirst.add(mergeCell);
                    for (int i = lastColumn + 1; i < first.size(); i++) {
                        newFirst.add(first.get(i));
                    }

                    tmpCells.set(firstRow, newFirst);
                    // 处理合并区域其他行
                    for (int i = firstRow + 1; i < lastRow; i++) {
                        List<JSONObject> list = tmpCells.get(i);
                        List<JSONObject> newList = new ArrayList<>();


                        for (int j = 0; j < firstColumn; j++) {
                            newList.add(list.get(j));
                        }
                        for (int j = lastColumn + 1; j < list.size(); j++) {
                            newList.add(list.get(j));
                        }
                        // 移除被合并单元格
                        tmpCells.set(i, newList);
                    }
                }
            }

            // 从全局设置cell宽度
            int totalColWidth = sheetColWidthList.stream().mapToInt(Integer::intValue).sum();
            for (int i = 0; i < tmpCells.size(); i++) {
                List<JSONObject> list = tmpCells.get(i);
                // 处理该行
                for (int j = 0; j < list.size(); j++) {
                    JSONObject currentCell = list.get(j);
                    if (currentCell.containsKey("rowspan") && currentCell.containsKey("colspan")) {
                        Integer colspan = currentCell.getInteger("colspan");
                        float value = 0.0f;
                        for (int k = j; k < j + colspan; k++) {
                            value += ((sheetColWidthList.get(k) / (float) totalColWidth) * 100);
                        }
                        currentCell.put("width", Math.round(value));
                        j = j + colspan;
                    } else {
                        float value = (sheetColWidthList.get(j) / (float) totalColWidth) * 100;
                        currentCell.put("width", Math.round(value));
                    }
                }
            }

            // 处理freemarker list
            // 长列表分块，表头必须在同一行，必须相邻，必须占据完整一行。
            for (int i = 0; i < tmpCells.size(); i++) {
                List<JSONObject> originRow = tmpCells.get(i);
                // 判断该行是否有freemarker list
                boolean freemarkerListFlag = false;
                for (int j = 0; j < originRow.size(); j++) {
                    JSONObject cell = originRow.get(j);
                    if(!cell.containsKey("elements")){
                        continue;
                    }
                    for (int k = 0; k < cell.getJSONArray("elements").size(); k++) {
                        JSONObject para = cell.getJSONArray("elements").getJSONObject(k);
                        if(!para.containsKey("text")){
                            break;
                        }
                        String text = para.getString("text");
                        // 匹配 ${xx.xxx} xx为list变量名，xxx为item属性名
                        if (StringUtils.isNotBlank(text) &&
                                text.startsWith("${") &&
                                text.endsWith("}") &&
                                text.contains(".")) {
                            freemarkerListFlag = true;
                            break;
                        }
                    }
                    if(freemarkerListFlag){
                        break;
                    }
                }
                if(!freemarkerListFlag){
                    continue;
                }
                // 当前行为freemarker list 进行填充数据
                // 首先暂存list，并从tmpCells中移除该行

                // copy list，按顺序填充jsonData中的数据，每填充一组就移除一组
                List<List<JSONObject>> fillRows = JSONObjectUtil.fillListData(originRow,requestJsonData);
                // 移除cell，将fillRows填充到该位置

                // 然后将填充好的copy list插入指定位置
                // 如果数据非空，则继续copy list，进行填充

                // 这种按顺序填充，天然支持分chunk

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
            // 由于将整个sheet解析为一个完整的table，因此不再设置chunkSize
            // table.put("chunkSize",2);

            BaseResponse response = PdfGenerator.createPDFV2ByStr(jsonData, jsonObject.toJSONString());
            if (response.getSuccess()) {
                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        TemplateUtils.transfer("{\n" +
                "    \"name\":\"测试名字\"\n" +
                "}");

    }
}
