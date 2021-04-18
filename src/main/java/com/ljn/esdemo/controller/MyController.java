package com.ljn.esdemo.controller;/**
 * @author ljn
 * @date 2021/4/17 0017 10:17
 * @version 1.0
 */

import com.ljn.esdemo.entity.User;
import com.ljn.esdemo.exception.BusinessException;
import com.ljn.esdemo.response.ResultResponse;
import com.ljn.esdemo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: lijienuo
 * @Date: 2021/4/17 0017 10:17
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    Service service;

    @GetMapping("/")
    public ResultResponse getCacheInfo(@RequestParam("car") String car) {
        return ResultResponse.ok(service.getCacheInfo(car));
    }


    @PostMapping(value="/post")
    public ResultResponse  methodName(@RequestBody User user) throws BusinessException {
        return ResultResponse.ok(service.putCache(user));
    }


}
