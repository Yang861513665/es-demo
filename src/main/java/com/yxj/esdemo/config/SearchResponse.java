package com.yxj.esdemo.config;

import lombok.Data;

/**
 * @author yangxj
 * @date 2019/7/13
 */
@Data
public class SearchResponse {
    private long took;
    private boolean time_out;
    private Shards _shards;
}
