package com.yxj.esdemo.repository;

import com.yxj.esdemo.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person,Integer> {

    List<Person> findByNameStartsWith(String namePrefix);
    Page<Person> findByAgeGreaterThan(Integer age, Pageable pageable);
    /**
     * term 查询被放置在 constant_score 中，转成不评分的 filter。这种方式可以用来取代只有 filter 语句的 bool 查询。
     * */
    @Query("{\"query\":{\"bool\":{\"must\":{\"range\":{\"age\":{\"gte\":?0,\"lt\":?1}}}}},\"size\":100,\"from\":0}")
//    @Query("{\"query\":{\"constant_score\":{\"filter\":{\"range\":{\"age\":{\"gte\":?0,\"lt\":?1}}}}}}")
    List<Person> findByAgeBetween(Integer age1,Integer age2);

    @Query("{\"query\":{\"constant_score\":{\"filter\":{\"term\":{\"id\":?0}}}}}")
    Person findById(Integer id);

    /**
     * {
     *     "match": { "title": "brown fox"}
     * }
     * || 等价
     * {
     *   "bool": {
     *     "should": [
     *       { "term": { "title": "brown" }},
     *       { "term": { "title": "fox"   }}
     *     ]
     *   }
     * }
     * */
    @Query("{\"query\":{\"bool\":{\"must\":{\"term\":{\"name\":\"?0\"}}}}}")
    List<Person> findByName(String name);

    @Query("{\"query\":{\"regexp\":{\"name\":\"yangxj.+\"}}}")
    List<Person> findByNamePrefix(String namePrefix);

}
