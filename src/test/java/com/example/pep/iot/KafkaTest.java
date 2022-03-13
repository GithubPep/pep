package com.example.pep.iot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

/**
 * @author LiuGang
 * @since 2022-03-13 9:25 PM
 */
@Slf4j
@SpringBootTest
public class KafkaTest {


    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void sendMsg() {
        kafkaTemplate.send("first-topic", "hello kafka");
    }
}
