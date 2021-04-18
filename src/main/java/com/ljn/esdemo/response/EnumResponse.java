package com.ljn.esdemo.response;

/**
 * @author ljn
 * @version 1.0
 * @date 2021/4/17 0017 8:40
 */
public enum EnumResponse {


    SUCCESS(200, "操作成功"),

    FAIL_400(400, "资源无法找到"),

    FAIL_500(500, "内部错误");

    private int code;
    private String msg;
    EnumResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
