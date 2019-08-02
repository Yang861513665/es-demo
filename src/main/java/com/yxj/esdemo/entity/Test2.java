package com.yxj.esdemo.entity;

/**
 * @author yangxj
 * @date 2019/7/19
 * @desc 对100万数据求和
 */

public class Test2 {
    public static void main(String[] args) {
        int [] numbers = new int[1000000];
        int[] results = new int[10];
        for (int i = 0; i < 10; i++) {
            int j=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 100000*j; k < 100000*(j+1); k++) {
                        results[j]+=numbers[k];
                    }
                }
            }).start();

            int result=0;
            for (int result_ : results) {
                result+=result_;
            }
            System.out.println("result===>"+result);
        }

    }
}
