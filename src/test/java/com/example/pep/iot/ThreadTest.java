package com.example.pep.iot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuGang
 * @since 2022-03-05 8:53 PM
 */
@Slf4j
public class ThreadTest {


    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        log.info("start");


    }


}
