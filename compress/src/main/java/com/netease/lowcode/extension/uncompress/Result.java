package com.netease.lowcode.extension.uncompress;

import com.netease.lowcode.core.annotation.NaslStructure;

import java.util.HashMap;
import java.util.Map;

@NaslStructure
public class Result {

    public Boolean success;
    public String msg;
    public String trace;
    public Map<String, String> data = new HashMap<>();

    public Result add(String key, String value) {
        this.data.put(key, value);
        return this;
    }

    public static Result OK() {
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg("OK");
        return result;
    }

    public static Result FAIL(String msg) {
        return FAIL(msg, "");
    }

    public static Result FAIL(String msg, String trace) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(msg);
        result.setTrace(trace);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
