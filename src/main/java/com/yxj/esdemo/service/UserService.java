package com.yxj.esdemo.service;

import org.springframework.stereotype.Service;

/**
 * @author yangxj
 * @date 2019/7/15
 */
@Service
public class UserService {
    private String name;

    public void test(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
