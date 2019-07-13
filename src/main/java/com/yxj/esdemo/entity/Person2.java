package com.yxj.esdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author yangxj
 * @date 2019/7/12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "people",type = "person2")
public class Person2 {
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Long)
    private Integer age;
//  fielddata 指定该属性能否进行排序，因为es中的text类型是不能进行排序（已经分词了）
    @Field(type = FieldType.Keyword)
    private String group;
    //yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis
    //创建索引时指定date类型format为"yyyy-MM-dd HH:mm:ss"，限制只能接受"yyyy-MM-dd HH:mm:ss"格式的date字符串
//    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date)
    private Date birthday;
    /**
     *
     * 关于时间类型的查询的记录（坑！！！）
     * （1）默认Date 类型  存document时，数据保存为long类型 比如： new Date（）---》  1921213131
     *  这个时候，要按区间查询时，需要传 long类型的区间；
     *
     * （2）若使用@JsonFormat注解指定在es中存放为某种格式的字符串时，那么查询时，需要传该格式类型的时间字符串值区间
     * */
}
