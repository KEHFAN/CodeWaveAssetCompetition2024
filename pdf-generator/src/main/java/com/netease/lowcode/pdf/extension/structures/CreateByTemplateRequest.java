package com.netease.lowcode.pdf.extension.structures;

import com.netease.lowcode.core.annotation.NaslStructure;

@NaslStructure
public class CreateByTemplateRequest {

    public String templateUrl;
    public String jsonData;
    public String exportFileName;
}
