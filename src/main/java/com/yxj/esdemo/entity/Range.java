package com.yxj.esdemo.entity;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxj
 * @date 2019/7/17
 */
@Data
public class Range {
    private Integer start;
    private Integer end;

    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<?> future1 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future<?> future3 = executorService.submit(() -> {
            try {
                int i = 10/0;
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ArrayList<Future<?>> futures = Lists.newArrayList(future1, future2, future3);
        for (Future<?> future : futures) {
            while (!future.isDone()){

            }
        }
        executorService.shutdown();
        System.out.println("耗时："+stopwatch.elapsed(TimeUnit.SECONDS)+"s");
    }
}

