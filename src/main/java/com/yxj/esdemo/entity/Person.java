package com.yxj.esdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "people",type = "person")
public class Person {
    private Integer id;
    private String name;
    private Integer age;
    @Field(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date birthday;


}
