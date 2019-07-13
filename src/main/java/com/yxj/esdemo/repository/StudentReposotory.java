package com.yxj.esdemo.repository;

import com.yxj.esdemo.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author yangxj
 * @date 2019/7/13
 */
public interface StudentReposotory extends ElasticsearchRepository<Student,Integer> {
}
