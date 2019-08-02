package com.yxj.esdemo;

import com.alibaba.fastjson.JSON;
import com.yxj.esdemo.entity.Student;
import com.yxj.esdemo.repository.StudentReposotory;
import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author yangxj
 * @date 2019/7/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Student2Test {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    StudentReposotory studentReposotory;

    @Test
    public void initDate(){
        String[] schools = new String[]{"SCHOOl-1","SCHOOL-2"};
        String[] classes = new String[]{"class-1","class-2","class-3"};
        int[] args = new int[]{23,25,48,52,14};
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        calendar.setTime(new Date());
        ArrayList<Student> students = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i,"stu"+i,args[random.nextInt(args.length)],calendar.getTime(),classes[random.nextInt(classes.length)],schools[random.nextInt(schools.length)]));
        }
        studentReposotory.saveAll(students);
    }
    @Test
    public void testAgg(){
        // 统计各个学校的各个班级学生年纪和
        TermsAggregationBuilder termsBuilder = AggregationBuilders.terms("school_group").field("schoolNo");
        TermsAggregationBuilder termsBuilder2 = AggregationBuilders.terms("class_group").field("classNo");
        // 查总数
        SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sum_ages").field("age");
        // 按某字段去重
//        ValueCountAggregationBuilder countBuilder = AggregationBuilders.count("count_id").field("id");
        CardinalityAggregationBuilder cardinalityBuilder = AggregationBuilders.cardinality("quchong_age").field("age");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("people")
                .withTypes("student")
                .addAggregation(termsBuilder.subAggregation(termsBuilder2.subAggregation(cardinalityBuilder))).build();
        HashMap<String, List<Map<String,Double>>> map = new HashMap<>();
        elasticsearchTemplate.query(searchQuery,response -> {
            StringTerms schoolGroup = response.getAggregations().get("school_group");
            List<StringTerms.Bucket> buckets = schoolGroup.getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
               StringTerms classGroup= bucket.getAggregations().get("class_group");
                List<StringTerms.Bucket> bucketsStb= classGroup.getBuckets();
                ArrayList<Map<String, Double>> maps = new ArrayList<>();
                for (StringTerms.Bucket bucketStb : bucketsStb) {
                    InternalCardinality cardinality= bucketStb.getAggregations().get("quchong_age");
                    HashMap<String, Double> mapStb = new HashMap<>();
                    mapStb.put(bucketStb.getKeyAsString(),cardinality.value());
                    maps.add(mapStb);
                }
            map.put(bucket.getKeyAsString(),maps);
            }
            System.out.println(map);
            return null;
        });
    }
    @Test
    public void testAgg2(){
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "stu7");
        TermQueryBuilder termQuery2 = QueryBuilders.termQuery("name", "stu5");

//        RangeQueryBuilder rangeBuilder = QueryBuilders.rangeQuery("createdAt").from("2019-06-25 00:00:00").to("2019-06-27 00:00:00");
//        SumAggregationBuilder sumBuilder = AggregationBuilders.sum("amount_sum").field("amount");
//        SumAggregationBuilder sumBuilder_ = AggregationBuilders.sum("used_amount_sum").field("used_amount");
        CardinalityAggregationBuilder classCount = AggregationBuilders.cardinality("class_count").field("classNo");
        CardinalityAggregationBuilder schoolCount = AggregationBuilders.cardinality("school_count").field("schoolNo");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("people")
                .withTypes("student")
                .addAggregation(schoolCount)
                .addAggregation(classCount)
//                .withQuery(QueryBuilders.boolQuery().should(termQuery).should(termQuery2))
//                .addAggregation(sumBuilder)
//                .addAggregation(sumBuilder_)
                .build();
        elasticsearchTemplate.query(searchQuery,response -> {
            InternalCardinality classCard =  response.getAggregations().get("class_count");
            InternalCardinality schoolCard =  response.getAggregations().get("school_count");
            System.out.println("class_count:" + classCard.getValue());
            System.out.println("school_count:" +schoolCard.getValue());
            return null;
        });
    }
    @Test
    public void test(){
        /**
         * 积分发放查询
         * */
            // 构建时间区间（增量） （暂定义时间存储es为 yyyy-MM-dd HH:mm:ss）
            RangeQueryBuilder rangeBuilder = QueryBuilders.rangeQuery("createdAt").from("2019-04-16 00:00:00").to("2019-04-16 23:59:59");
            // 构建积分类型为UP（UP代表积分获取，DOWN代表积分消耗）
            TermQueryBuilder termQuery = QueryBuilders.termQuery("operateType", "UP");
            // 按航道名称(对应es bizSourceType)
            TermQueryBuilder termQuery2 = QueryBuilders.termQuery("bizSourceType", "C3");
            TermQueryBuilder termQuery3 = QueryBuilders.termQuery("rule", "8");
            // 积分发放用户求和（去重）
            CardinalityAggregationBuilder cardBuilder = AggregationBuilders.cardinality("lmid_count").field("lmid");

//            Map<String, Double> result = new HashMap<>();
                    // 积分发放动作人数统计
                    // 组合search
                    SearchQuery searchQuery = new NativeSearchQueryBuilder()
                            .withIndices("middle_point_details")
                            .withTypes("point_details")
                            .withQuery(QueryBuilders.boolQuery()
                                    // todo 用户动作：积分发放动作人数统计【时间区间使用增量？？】
                                    .must(rangeBuilder)
                                    .must(termQuery)
                                    .must(termQuery2)
                                    .must(termQuery3))
                            .addAggregation(cardBuilder)
                            .build();
                    elasticsearchTemplate.query(searchQuery, response -> {
                        InternalCardinality cardinality = response.getAggregations().get("lmid_count");
                        System.out.println(cardinality.getValue());
                        System.out.println(cardinality.value());
                        return cardinality.value();
                    });
                }
            }
//            log.info("-----es积分发放统计完毕, 结果: {}--------", JSON.toJSONString(result));
//            return result;
//        }
//    }
//}
