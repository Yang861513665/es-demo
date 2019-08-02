package com.yxj.esdemo;

import lombok.Data;

/**
 * @author yangxj
 * @date 2019/7/14
 */
public enum OperatorType {

    DELETE("delete","删除"),
    PUT("put","更新"),
    POST("post","新增");

    public final String name;
    public final String descrition;

    OperatorType(String name, String description) {
        this.name =name;
        this.descrition =description;
    }
}
