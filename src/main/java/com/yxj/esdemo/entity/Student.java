package com.yxj.esdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author yangxj
 * @date 2019/7/13
 */
@Document(indexName = "people",type = "student")
@Data
@AllArgsConstructor
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
}
