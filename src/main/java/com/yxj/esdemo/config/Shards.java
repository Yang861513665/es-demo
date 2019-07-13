package com.yxj.esdemo.config;

import lombok.Data;

/**
 * @author yangxj
 * @date 2019/7/13
 */
@Data
public class Shards{
    private long total;
    private long successful;
    private long skipped;
    private long failed;
}
