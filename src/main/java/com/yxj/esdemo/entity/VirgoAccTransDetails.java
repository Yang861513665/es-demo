package com.yxj.esdemo.entity;

/**
 * @author yangxj
 * @date 2019/7/19
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author yangxj
 * @date 2019/7/13
 *   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 *   `member_id` bigint(20) NOT NULL COMMENT '会员id',-->lmid
 *   `account_id` bigint(20) NOT NULL COMMENT '账户id',
 *   `gl_code` varchar(64) NOT NULL COMMENT '账户科目',
 *   `trade_no` varchar(64) NOT NULL COMMENT '账户中心交易流水号',
 *   `biz_no` varchar(32) NOT NULL COMMENT '外部业务号-冗余',
 *   `biz_type` varchar(16) NOT NULL COMMENT '外部业务类型代码-冗余',
 *   `hash_code` varchar(32) NOT NULL COMMENT '唯一索引，用于幂等',
 *   `rule` varchar(32) NOT NULL COMMENT '规则id，唯一值',
 *   `amount` bigint(20) NOT NULL COMMENT '数额(类型为DOWN时表示已扣减)',
 *   `used_amount` bigint(20) NOT NULL COMMENT '已核销数额(类型为DOWN时表示已偿还)',
 *   `rest_amount` bigint(20) NOT NULL COMMENT '剩余数额(类型为DOWN时为剩余负债)',
 *   `return_amount` bigint(20) DEFAULT NULL COMMENT '已退数额(只存在类型为UP)',
 *   `trade_money` bigint(20) DEFAULT NULL COMMENT '交易金额',
 *   `compute_money` bigint(20) DEFAULT NULL COMMENT '计算金额',
 *   `refund_money` bigint(20) DEFAULT NULL COMMENT '累计退款金额',
 *   `operate_type` varchar(16) NOT NULL COMMENT 'UP(增加）, DOWN(减少)',
 *   `status` varchar(16) NOT NULL COMMENT '明细状态 INIT:未使用, USING:使用中, USED:已使用, EXPIRED:已过期, DEBT: 负债REPAYING:偿还中, REPAID:已偿还,UNPROCESSED',
 *   `reason` varchar(128) DEFAULT NULL COMMENT '变更原因',
 *   `traded_at` datetime DEFAULT NULL COMMENT '交易时间',
 *   `expired_at` datetime DEFAULT NULL COMMENT '失效时间, 该字段使用与否取决 ALLOW_EXPIRE 规则',
 *   `extra_json` varchar(1024) DEFAULT NULL COMMENT '扩展字段',
 *   `created_at` datetime NOT NULL COMMENT '创建时间',
 *   `updated_at` datetime NOT NULL COMMENT '更新时间',
 *   `biz_source` varchar(255) DEFAULT NULL,
 *   `biz_source_type` varchar(255) DEFAULT NULL
 */
@Data
@Document(indexName = "middle_point",type = "point_details")
public class VirgoAccTransDetails{
    @Id
    private long id;
    @Field(type = FieldType.Long)
    private long lmid;
    @Field(type = FieldType.Keyword)
    private String tenant;
    @Field(type = FieldType.Long)
    private long accountId;
    @Field(type = FieldType.Keyword)
    private String glCode;
    @Field(type = FieldType.Keyword)
    private String tradeNo;
    @Field(type = FieldType.Long)
    private long tradeMoney;
    @Field(type = FieldType.Long)
    private long computeMoney;
    @Field(type = FieldType.Long)
    private long refundMoney;
    @Field(type = FieldType.Keyword)
    private String status;
//    @Field(type = FieldType.Keyword)
//    private String reason;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tradedAt;
//    @Field(type = FieldType.Date)
//    private Date expiredAt;
//    @Field(type = FieldType.Keyword)
    private String bizSource;
    @Field(type = FieldType.Keyword)
    private String bizSourceType;
    @Field(type = FieldType.Keyword)
    private String bizNo;
    @Field(type = FieldType.Keyword)
    private String bizType;
    @Field(type = FieldType.Keyword)
    private String rule;
    @Field(type = FieldType.Long)
    private long amount;
    @Field(type = FieldType.Long)
    private long usedAmount;
    @Field(type = FieldType.Long)
    private long restAmount;
    @Field(type = FieldType.Long)
    private long returnAmount;
    @Field(type = FieldType.Keyword)
    private String operateType;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;
    @Field(type = FieldType.Keyword)
    private String ext1;
    @Field(type = FieldType.Keyword)
    private String ext2;
    @Field(type = FieldType.Keyword)
    private String ext3;
}
