package com.yxj.esdemo.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author yangxj
 * @date 2019/7/17
 */
@Entity
@Table(name="tb_user")
@Builder
@Data
public class TbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Double money;
    @Column(columnDefinition = "date")
    private Date birthday;
    @Column(name = "create_time",columnDefinition = "date")
    private Date createTime;
    @Transient
    private Double sum;

    public static void main(String[] args) {
        String ranges = "[{'start':'0','end':'100'},{'start':'200','end':'500'},{'start':'100','end':'200'}]";
        List<Range> rangeList = JSON.parseArray(ranges, Range.class);
        rangeList.sort(Comparator.comparingInt(Range::getStart));
        Integer temp = rangeList.get(0).getEnd();
        rangeList.remove(rangeList.get(0));
        for (Range range : rangeList) {
            if(temp>range.getStart()){
                throw new RuntimeException("有重复！！");
            }
            temp = range.getEnd();
        }
        System.out.println(rangeList);
    }
}
