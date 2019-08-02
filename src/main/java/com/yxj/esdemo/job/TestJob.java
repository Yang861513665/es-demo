package com.yxj.esdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author yangxj
 * @date 2019/7/30
 */
@Component
@Slf4j
@ConditionalOnExpression("'${spring.datasource.username}'.contains('root11')")
public class TestJob {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Scheduled(cron = "0/5 * * * * ?") public void generateSchedule(){
        try {
            log.info("scheduled now date: {}",simpleDateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
