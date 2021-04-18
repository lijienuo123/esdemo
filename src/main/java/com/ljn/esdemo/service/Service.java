package com.ljn.esdemo.service;

import com.ljn.esdemo.cache.Cache;
import com.ljn.esdemo.cache.CacheManager;
import com.ljn.esdemo.entity.User;
import com.ljn.esdemo.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: lijienuo
 * @Date: 2021/4/17 0017 10:20
 * @Version: 1.0
 */
@org.springframework.stereotype.Service
public class Service {

    public Object getCacheInfo(String type) {
        Cache cacheInfo = CacheManager.getCacheInfo(type);
        if (cacheInfo ==   null) {

            /*shouda*/
            this.putCache();
            return CacheManager.getCacheInfo(type);
        }
        return cacheInfo;
    }

    public User putCache(User user) throws BusinessException {
        if (CacheManager.getCacheInfo("person") != null) {
            CacheManager.clearAll("person");
        }


        Cache cache = new Cache() {{
            setKey("1");
            setValue(user);
        }};
        if (true) {
            throw new BusinessException("yy");
        }
        CacheManager.putCache("person", cache, 5008);
        return user;
    }

    public List<String> putCache() {

        List<String> list = new ArrayList() {{
            add("ljn1");
            add("魏超鞥1");
            add("开槽1");
        }};
        Cache cache = new Cache() {{
            setKey("1组");
            setValue(list);
        }};

        CacheManager.putCache("person", cache);
        return list;
    }
}
