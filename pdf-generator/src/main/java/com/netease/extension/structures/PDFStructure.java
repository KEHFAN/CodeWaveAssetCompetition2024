package com.netease.extension.structures;


import com.netease.lowcode.core.annotation.NaslStructure;

import java.util.List;

@NaslStructure
public class PDFStructure {
    /**
     * *.pdf
     */
    public String fileName = "test.pdf";
    /**
     * 全局字体
     */
    public FontStructure font = new FontStructure();

    /**
     * 全局字体大小
     */
    public Integer fontSize = 7;
    /**
     * 纸张大小
     */
    public String pageSize = "A4";
    /**
     * 纸张方向
     */
    public Boolean rotate = false;
    /**
     * 页边距
     */
    public Integer marginLeft = 36;
    public Integer marginRight = 36;
    public Integer marginTop = 36;
    public Integer marginBottom = 36;

    /**
     * 水印 waterMask
     */
    /**
     * 页眉 header
     */
    /**
     * 页脚 footer
     */
    /**
     * 页码 pageNumber
     */

    /**
     * 节点 按顺序解析
     */
    public List<PDFNode> nodes;
}
