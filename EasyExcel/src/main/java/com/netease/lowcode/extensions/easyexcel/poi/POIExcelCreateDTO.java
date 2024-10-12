package com.netease.lowcode.extensions.easyexcel.poi;

import com.netease.lowcode.core.annotation.NaslStructure;
import org.apache.commons.lang3.StringUtils;

@NaslStructure
public class POIExcelCreateDTO {

    public String exportFileName;
    public String jsonData;

    public void validate() {
        if (StringUtils.isBlank(exportFileName)) {
            throw new RuntimeException("文件名不能为空");
        }
        if (StringUtils.isBlank(jsonData)) {
            throw new RuntimeException("数据不能为空");
        }
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
