package com.netease.lowcode.extensions.poi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.netease.lowcode.core.annotation.NaslLogic;
import com.netease.lowcode.extensions.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

public class POIExcelCreate {



    //@NaslLogic
    public static <T> String poiCreateXls(POIExcelCreateDTO request, Class<T> clazz) {

        request.validate();

        ExcelData excelData = new ExcelData();
        // 设置属性与表头的映射
        excelData.setName(request.getExportFileName());
        // 一个逻辑目前仅支持一个sheet
        SheetData sheetData = new SheetData();

        // 1. 当列很多时，需要手动繁琐的add，最好通过 解析 T 泛型
        // 2. 解析Structure属性的@Label注解参数，获取表头，列的属性：列宽、列的order、列值的条件
        // 4. 合并单元格的情况（暂不支持，涉及到计算）
        // 5. 导出动态列 如何解决。（在structure中使用Map表示动态的列，属性如何设置？）
        // 6. 导出日期，时间格式、时区的问题。 其他类型映射到excel的问题
        // 7. 小数点处理的问题，保留小数的问题；
        // 8. 列值条件，引入groovy语法
        // 9. 导出多个sheet?（暂不支持）

        CommonHandler.parseTitle(clazz, sheetData);
        // 添加表头
        CommonHandler.addTitle(sheetData);
        CommonHandler.addData(request, sheetData);

        excelData.setSheetList(Collections.singletonList(sheetData));
        CommonHandler.createXls(excelData);

        return null;
    }

    @NaslLogic
    public static String poiCreateXlsx() {
        return null;
    }


    public static String poiCreateXlsAsync() {
        return null;
    }

    public static String poiCreateXlsxAsync() {
        return null;
    }

}
