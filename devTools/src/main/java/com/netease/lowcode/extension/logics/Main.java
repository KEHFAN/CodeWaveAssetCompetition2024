package com.netease.lowcode.extension.logics;

import com.netease.lowcode.core.annotation.NaslLogic;
import com.netease.lowcode.extension.command.CmdChains;
import com.netease.lowcode.extension.command.CmdConst;
import com.netease.lowcode.extension.command.Command;

import java.util.Collections;

public class Main {

    @NaslLogic
    public static String ls(String dir) {
        Command.exec(CmdChains.LS(dir));
        return "";
    }

    @NaslLogic
    public static String pwd() {
        Command.exec(Collections.singletonList(CmdConst.PWD));
        return "";
    }

}
