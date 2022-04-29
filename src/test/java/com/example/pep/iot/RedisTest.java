package com.example.pep.iot;

import com.example.pep.iot.test.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LiuGang
 * @since 2022-03-10 3:11 PM
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonService redissonService;

    @Test
    public void monitorRedis() {
        redisTemplate.opsForValue().set("name", "pep");
        Object name = redisTemplate.opsForValue().get("name");
        log.info("name:{}", name);
    }

    @Test
    public void get() {
        Object name = redisTemplate.opsForValue().get("name");
        log.info("name:{}", name);
    }

    @Test
    public void testLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        log.info("start ");
        @SuppressWarnings("unchecked")
        CompletableFuture<Void>[] futures = new CompletableFuture[3];
        for (int i = 0; i < 3; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> redissonService.lock(), executorService);
            futures[i] = future;
        }
        CompletableFuture.allOf(futures).join();
        log.info("stop ");
    }

}
