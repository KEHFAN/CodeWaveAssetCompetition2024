package com.netease.lowcode.extension.structures;

import com.alibaba.fastjson2.JSONObject;
import com.itextpdf.layout.element.IBlockElement;
import com.netease.lowcode.extension.itextpdf.NodeCreator;

public enum NodeTypeEnum {

    Paragraph{
        public IBlockElement exec(JSONObject obj) {
            return NodeCreator.paragraph(obj);
        }
    },
    Table{
        public IBlockElement exec(JSONObject obj) {
            return NodeCreator.table(obj);
        }
    },
    Cell{
        public IBlockElement exec(JSONObject obj){
            return NodeCreator.cell(obj);
        }
    },
    CheckBox{
        public IBlockElement exec(JSONObject obj) {
            return NodeCreator.checkBox(obj);
        }
    },
    LineSeparator{
        @Override
        public IBlockElement exec(JSONObject obj) {
            return NodeCreator.lineSeparator(obj);
        }
    };

    public abstract IBlockElement exec(JSONObject obj);
}
