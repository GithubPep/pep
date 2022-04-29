package com.example.pep.iot.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 测试redisson
 *
 * @author LiuGang
 * @since 2022-04-22 20:31
 */
@Slf4j
@Component
public class RedissonService {

    private static final String LOCK = "redis";

    @Resource
    private RedissonClient redissonClient;

    @SneakyThrows
    public void lock() {
        RLock lock = redissonClient.getLock(LOCK);
        boolean b = lock.tryLock(2, 10, TimeUnit.SECONDS);
        if (b) {
            try {
                log.info("do some thing ");
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException ignore) {
            } finally {
                lock.unlock();
                log.info("do over ");
            }
        }
    }
}
