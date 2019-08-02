package com.yxj.esdemo.controller;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.yxj.esdemo.entity.Student;
import com.yxj.esdemo.entity.UserRequest;
import com.yxj.esdemo.repository.TbUserRepository;
import com.yxj.esdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxj
 * @date 2019/7/14
 */

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TbUserRepository tbUserRepository;
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("test")
    public UserRequest test(UserRequest request){
        System.out.println(request.getOperatorType().name);
        userService.test(request.getName());
        return request;
    }
    @RequestMapping("test2")
    public String test2(){
        return userService.getName();
    }

    @RequestMapping("test3")
    public Object test3(Integer[] ids){
        System.out.println(Arrays.asList(ids));
        return tbUserRepository.findByIdIn(Arrays.asList(ids));
    }

    @RequestMapping("from")
    public String from(String msg){
        Stopwatch stopwatch = Stopwatch.createStarted();
        restTemplate.getForObject("http://localhost:8081/to?msg" + msg, String.class);
        log.info("耗时: "+stopwatch.elapsed(TimeUnit.SECONDS)+"s");
        return msg;
    }

    @RequestMapping("to")
    public String to(String msg) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "hello: "+msg;
    }
    /**
     * 测试表单重复提交解决方案
     */
    @RequestMapping("repeate")
    public String repate(){
        return null;
    }

}
