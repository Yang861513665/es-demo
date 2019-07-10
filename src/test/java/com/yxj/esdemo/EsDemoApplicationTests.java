package com.yxj.esdemo;

import com.google.common.collect.Iterators;
import com.google.common.primitives.Ints;
import com.yxj.esdemo.entity.Person;
import com.yxj.esdemo.repository.PersonRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.GetResultMapper;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDemoApplicationTests {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void contextLoads() {
        //==========init data==============
//        List<Integer> ages = Ints.asList(23, 19, 35, 60, 17, 15, 12, 21);
//        Random random = new Random();
//        ArrayList<Person> persons = new ArrayList<>();
//        for (int i = 1; i < 100; i++) {
//            Person person = new Person(i, "yangxj"+i, ages.get(random.nextInt(ages.size()-1)),new Date());
//            persons.add(person);
//        }
//        persons.add(new Person(102,"yang",25,new Date()));
          // ======save multiple data======
//        personRepository.save(persons)
//      ============delete by id============================
//       personRepository.delete(1);
        // term query
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("name", "yangxj01")).build();
// =================find by id==========================
        //        GetQuery getQuery = new GetQuery();
//        getQuery.setId("1");
//        Person person = elasticsearchTemplate.queryForObject(getQuery, Person.class);
//  ===============================================
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(regexpQuery("name","yangxj*")).build();
//        List<Person> people = elasticsearchTemplate.queryForList(searchQuery, Person.class);
        //  ===============================================
//        List<Person> personList = personRepository.findByNameStartsWith("yangxj");
//        Iterable<Person> all = personRepository.findAll();
        List<Person> peoples = personRepository.findByAgeBetween(19, 25);
        peoples.stream().forEach(person -> System.out.println(person.getAge()));
//        Person person = personRepository.findById(20);
//        System.out.println(person);
//        List<Person> peoples = personRepository.findByName("yang");
//        List<Person> peoples = personRepository.findByNamePrefix("yangxj*");
//        List<Person> peoples = personRepository.findByNameStartsWith("yangxj");
//        System.out.println(peoples.size());

    }



}
