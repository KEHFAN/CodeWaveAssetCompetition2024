package com.netease.lowcode.extensions.poi;

import com.netease.lowcode.core.annotation.NaslLogic;
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
import java.util.Objects;

public class POIExcelCreate {



    //@NaslLogic
    public static <T> String poiCreateXls(POIExcelCreateDTO request, Class<T> clazz) {

        request.validate();

        // 1. 当列很多时，需要手动繁琐的add，最好通过 解析 T 泛型
        // 2. 解析Structure属性的@Label注解参数，获取表头，列的属性：列宽、列的order、列值的条件
        // 4. 合并单元格的情况（暂不支持，涉及到计算）
        // 5. 导出动态列 如何解决。（在structure中使用Map表示动态的列，属性如何设置？）
        // 6. 导出日期，时间格式、时区的问题。 其他类型映射到excel的问题
        // 7. 小数点处理的问题，保留小数的问题；
        // 8. 列值条件，引入groovy语法
        // 9. 导出多个sheet?（暂不支持）

        // 解析表头数据：复杂表头：在label中 使用 主表头1-子表头2 会自动合并相同且相邻的主表头
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation[] annotations = declaredField.getAnnotations();
            if(Objects.nonNull(annotations)){
                for (Annotation annotation : annotations) {
                    String simpleName = annotation.annotationType().getSimpleName();
                    if(StringUtils.equals("Label",simpleName)){
                        try {
                            Object value = annotation.annotationType().getMethod("value").invoke(annotation);
                            System.out.println();
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        // 创建一行数据
        HSSFRow row = sheet.createRow(0);
        //     * NASL类型和Java类型匹配关系：
        //    * NASL：Boolean， Java：java.lang.Boolean
        //    * NASL：Integer， Java：java.lang.Long
        //    * NASL：String，      Java：java.lang.String
        //    * NASL：Time，        Java：java.time.LocalTime
        //    * NASL：Date，        Java：java.time.LocalDate
        //    * NASL：DateTime，    Java：java.time.ZonedDateTime
        //    * NASL：Decimal， Java：java.math.BigDecimal
        //    * NASL：List，        Java：java.util.List
        //    * NASL：Map，     Java：java.util.Map
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ff");
        try {
            wb.write(new FileOutputStream("test.xls"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
