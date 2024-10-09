package com.netease.lowcode.extensions.poi;

import org.apache.poi.ss.usermodel.IndexedColors;

public class CellStyle {

    // 单元格背景填充颜色
    private String background;
    // 列表头
    private String title;
    // 指定列的顺序
    private Integer index;

    public short getBackground() {
        return IndexedColors.valueOf(this.background).getIndex();
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
