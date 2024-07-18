package com.netease.lowcode.extension.command;

import org.apache.commons.lang3.SystemUtils;

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
        String window = CmdConst.CAT(filename, "|", "Select", "-First", String.valueOf(num));
        String linux = CmdConst.HEAD(filename, num);
        return Collections.singletonList(SystemUtils.IS_OS_WINDOWS ? window : linux);
    }

    public static List<String> CAT_LAST(String filename, Integer num) {
        String window = CmdConst.CAT(filename, "|", "Select", "-Last", String.valueOf(num));
        String linux = CmdConst.TAIL(filename, num);
        return Collections.singletonList(SystemUtils.IS_OS_WINDOWS ? window : linux);
    }

    /**
     * 查看开头几行
     *
     * @param num
     * @return
     */
    public static List<String> CAT_NATIVE_LOG_FIRST(Integer num) {
        return CAT_FIRST("~/logs/biz/origin/native.log", num);
    }

    /**
     * 查看最后几行
     *
     * @param num
     * @return
     */
    public static final List<String> CAT_NATIVE_LOG_LAST(Integer num) {
        return CAT_LAST("~/logs/biz/origin/native.log",num);
    }
}
