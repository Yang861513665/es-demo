package com.yxj.esdemo.repository;

import com.yxj.esdemo.entity.Person2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author yangxj
 * @date 2019/7/12
 */
public interface Person2Repository extends ElasticsearchRepository<Person2,Integer> {
}
