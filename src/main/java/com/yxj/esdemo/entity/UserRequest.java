package com.yxj.esdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxj.esdemo.OperatorType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yangxj
 * @date 2019/7/14
 */
@Data
public class UserRequest {
    private String name;
    private OperatorType operatorType = OperatorType.POST;
//    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date date2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
