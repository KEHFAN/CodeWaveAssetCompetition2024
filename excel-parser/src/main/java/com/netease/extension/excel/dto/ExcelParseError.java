package com.netease.extension.excel.dto;

import lombok.Data;

@Data
public class ExcelParseError {
    public String sheetName;
    public String cellValue;
    public String cellName;
    public String errorMsg;
}
