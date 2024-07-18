package com.netease.lowcode.extension.command;

import java.util.Collections;
import java.util.List;

public class CmdChains {

    public static final List<String> LS_HOME = Collections.singletonList(CmdConst.LS("~"));
    public static final List<String> LS_BIZ = Collections.singletonList(CmdConst.LS("~/logs/biz"));
    public static final List<String> LS_ORIGIN = Collections.singletonList(CmdConst.LS("~/logs/biz/origin"));

    public static List<String> LS(String dir) {
        return Collections.singletonList(CmdConst.LS(dir));
    }

    public static List<String> CAT_FIRST(String filename, Integer num) {
        return Collections.singletonList(CmdConst.CAT(filename, "|", "Select", "-First", String.valueOf(num)));
    }

    public static List<String> CAT_LAST(String filename, Integer num) {
        return Collections.singletonList(CmdConst.CAT(filename, "|", "Select", "-Last", String.valueOf(num)));
    }

    /**
     * 查看开头几行
     *
     * @param num
     * @return
     */
    public static List<String> CAT_NATIVE_LOG_FIRST(Integer num) {
        return Collections.singletonList(CmdConst.CAT("~/logs/biz/origin/native.log",
                "|", "Select", "-First", String.valueOf(num)));
    }

    /**
     * 查看最后几行
     *
     * @param num
     * @return
     */
    public static final List<String> CAT_NATIVE_LOG_LAST(Integer num) {
        return Collections.singletonList(CmdConst.CAT("~/logs/biz/origin/native.log",
                "|", "Select", "-Last", String.valueOf(num)));
    }
}
