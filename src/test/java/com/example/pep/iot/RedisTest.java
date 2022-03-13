package com.example.pep.iot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author LiuGang
 * @since 2022-03-10 3:11 PM
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void monitorRedis() {
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info("memory");
        log.info("info:{}", info);
    }
}
