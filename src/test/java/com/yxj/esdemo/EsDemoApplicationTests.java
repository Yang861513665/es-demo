package com.yxj.esdemo;

import com.yxj.esdemo.entity.Person2;
import com.yxj.esdemo.repository.Person2Repository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    PersonRepository personRepository;
    @Autowired
    Person2Repository person2Repository;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
   @Test
   public void init(){
               String[] groups = new String[]{"G1","G2","G3"};
        int[] args = new int[]{23,25,48,52,14};
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        calendar.setTime(new Date());
        for (int i = 1; i < 10; i++) {
            calendar.add(Calendar.DATE,2);
            Person2 person = new Person2(i, "yangxj0"+i, args[random.nextInt(args.length)],groups[random.nextInt(groups.length)],calendar.getTime());
            person2Repository.save(person);
        }
   }

    @Test
    public void testAgg() throws ParseException {
        // 聚合查询。goodsSales是要统计的字段，sum_sales是自定义的别名
        SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sum_ages").field("age");
        AvgAggregationBuilder avgBuilder = AggregationBuilders.avg("avg_ages").field("age");
         // 操作： 按用户所属分组来对年龄求和,且group不为G2
        TermsAggregationBuilder termsBuilder = AggregationBuilders.terms("groups").field("group");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("people")
                .withTypes("person2")
                .withQuery(QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("group","G2")))
//                .addAggregation(sumBuilder)
                .addAggregation(termsBuilder.subAggregation(sumBuilder))
//                .addAggregation(sumBuilder)
                .build();
//        Page<Person2> search = person2Repository.search(searchQuery);
//        search.stream().forEach(System.out::println);
        List<Map<String,Double>> sumAgeByGroup=new ArrayList<>();
                elasticsearchTemplate.query(searchQuery, response -> {
//            InternalSum sum = (InternalSum) response.getAggregations().asList().get(0);
//            Aggregations aggregations = response.getAggregations();
            StringTerms terms = response.getAggregations().get("groups");
            List<StringTerms.Bucket> buckets = terms.getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
                HashMap<String, Double> map = new HashMap<>();
                InternalSum sum = bucket.getAggregations().get("sum_ages");
                map.put(bucket.getKeyAsString(),sum.value());
                sumAgeByGroup.add(map);
            }
//            List<? extends Terms.Bucket> buckets = terms.getBuckets();
//            buckets.stream().forEach(bucket->{
//                bucket.get
//            });
//            buckets.forEach(bucket-> System.out.println(bucket));
//            System.out.println(aggregations);
//            Aggregation aggregation = aggregations.asList().get(0);
//            return sum.getValue();
            return new ArrayList<>();
          });}
    @Test
    public void testAgg2(){
       /**
        * 需求： 按group分组，对年龄去重，求每个分组中的人数
        * */
        CardinalityAggregationBuilder cardBuilder = AggregationBuilders.cardinality("no_repeat_age").field("age");
        TermsAggregationBuilder termsBuilder = AggregationBuilders.terms("groups").field("group");
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("people")
                .withTypes("person2")
//                .addAggregation(sumBuilder)
                .addAggregation(cardBuilder.subAggregation(termsBuilder))
//                .addAggregation(sumBuilder)
                .build();
        elasticsearchTemplate.query(searchQuery,response -> {
            Aggregation noRepeatAge = response.getAggregations().get("no_repeat_age");
            InternalCardinality internalCardinality = (InternalCardinality) noRepeatAge;
            System.out.println(internalCardinality.value());
            return null;
        });
    }

}
