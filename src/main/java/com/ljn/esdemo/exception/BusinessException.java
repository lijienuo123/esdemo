package com.ljn.esdemo.exception;

public class BusinessException extends Exception {


    private Integer code;

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public    BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer   getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}