package com.ljn.esdemo.cache;

import lombok.Data;

/**
 * <p>Description: 缓存DTO</p>
 *
 * @author zhanglp
 */
@Data
public class Cache {
    private String key;//缓存ID
    private Object value;//缓存数据
    private long timeOut;//更新时间
    private boolean expired; //是否终止

    public Cache() {
        super();
    }

    public Cache(String key, Object value, long timeOut, boolean expired) {
        this.key = key;
        this.value = value;
        this.timeOut = timeOut;
        this.expired = expired;
    }
}