package com.yxj.esdemo;

import com.yxj.esdemo.entity.Result;
import com.yxj.esdemo.entity.TbUser;
import com.yxj.esdemo.repository.TbUserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangxj
 * @date 2019/7/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbUserTest {
    @Autowired
    TbUserRepository userRepository;

//    @Test
//    public void add(){
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(new Date());
//        ArrayList<TbUser> users = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            TbUser user = TbUser.builder().name("yangxj0" + i).money(200.00 + i).birthday(instance.getTime()).createTime(new Date()).build();
//            instance.add(Calendar.DATE,-1);
//            users.add(user);
//        }
//        userRepository.saveAll(users);
//    }
//    @Test
//    public void find() throws ParseException {
//        List<Long> byIdIn = userRepository.findByIdIn(Lists.newArrayList(2, 3));
//        System.out.println("lala");
//        System.out.println(byIdIn.get(1));
//    }
}
