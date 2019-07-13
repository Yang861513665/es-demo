package com.yxj.esdemo;

import com.yxj.esdemo.entity.Student;
import com.yxj.esdemo.repository.StudentReposotory;
import org.assertj.core.util.Lists;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
}
