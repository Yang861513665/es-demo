package com.yxj.esdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yangxj
 * @date 2019/7/13
 */
@Document(indexName = "people",type = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Long)
    private Integer age;
    @Field(type = FieldType.Date)
    private Date entrance;
    @Field(type = FieldType.Keyword)
    private String classNo;
    @Field(type = FieldType.Keyword)
    private String schoolNo;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException {
       Class clazz =  Class.forName("com.yxj.esdemo.entity.Result");
        Method method = clazz.getDeclaredMethod("show");
        method.setAccessible(true);
        try {
            Object result = method.invoke(clazz.newInstance(), "world");
            System.out.println(result);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
