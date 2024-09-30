package com.netease.lowcode.extension;

import org.springframework.http.HttpStatus;

public class UploadPatchHttpCodeException extends RuntimeException {
    private int httpCode;
    private String errorKey;
    private transient Object[] args;
    private String message;

    public UploadPatchHttpCodeException(String errorKey) {
        this.httpCode = 500;
        this.errorKey = errorKey;
    }

    public UploadPatchHttpCodeException(int httpCode, String errorKey) {
        this.httpCode = httpCode;
        this.errorKey = errorKey;
    }

    public UploadPatchHttpCodeException(int httpCode, String errorKey, Object... args) {
        this.httpCode = httpCode;
        this.errorKey = errorKey;
        this.args = args;
    }

    public UploadPatchHttpCodeException(int httpCode, Throwable t) {
        super(t);
        this.httpCode = httpCode;
        this.errorKey = t.getMessage();
    }

    public UploadPatchHttpCodeException(String errorKey, Throwable t) {
        super(t);
        this.httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.errorKey = errorKey;
    }

    public UploadPatchHttpCodeException(int httpCode, String errorKey, Throwable t) {
        super(t);
        this.httpCode = httpCode;
        this.errorKey = errorKey;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {return this.message;}
}

