package com.netease.lowcode.extension.logics;

import com.netease.lowcode.core.annotation.NaslLogic;
import com.netease.lowcode.extension.command.CmdChains;
import com.netease.lowcode.extension.command.CmdConst;
import com.netease.lowcode.extension.command.Command;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

public class Main {

    @NaslLogic
    public static String ls(String dir) {

        if (StringUtils.isBlank(dir)) {
            Command.exec(Collections.singletonList(CmdConst.LS));
            return "";
        }

        Command.exec(CmdChains.LS(dir));
        return "";
    }

    @NaslLogic
    public static String pwd() {
        Command.exec(Collections.singletonList(CmdConst.PWD));
        return "";
    }

}
