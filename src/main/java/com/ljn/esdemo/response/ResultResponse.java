/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：Wrapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.ljn.esdemo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author kml
 * 封装接口返回的消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse<T> implements Serializable {

    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    private int code;

    private String message;

    private T result;

    /**
     * 返回：200，“操作成功”和数据，否则程序中抛出异常即可
     *
     * @param o
     * @param <E>
     * @return
     */
    public static <E> ResultResponse<E> ok(E o) {
        return new ResultResponse<>(ResultResponse.SUCCESS_CODE, ResultResponse.SUCCESS_MESSAGE, o);
    }

}
