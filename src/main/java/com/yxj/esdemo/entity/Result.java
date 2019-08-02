package com.yxj.esdemo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author yangxj
 * @date 2019/7/18
 */
@Data
public class Result {
    private String key;
    private Double value;
    private String show(String msg){
        return show2(msg);
    }
    private static String show1(String msg){
        test();
        return "hello---->"+msg;
    }
    private static String show2(String msg){
        return "hello---->"+msg;
    }
    private static void test(){
        return;
    }

    public static void main(String[] args) {
        System.out.println(JSON.parseObject("{\"POINT_SUM\":{},\"MEMBER_COUNT\":{}}"));
    }
}
