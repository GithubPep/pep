package com.example.pep.iot.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiuGang
 * @since 2022-03-13 10:15 PM
 */
@Slf4j
@Component
public class KafkaConsumer {


    @KafkaListener(topics = {"first-topic"})
    private void consumer(List<ConsumerRecord<String, String>> records) {
        records.forEach(record -> {
            log.info("ack topic = {}, offset = {}, msg = {}", record.topic(), record.offset(), record.value());
            String messageData = record.value();
            //接受到的信息
            JSONObject jsonObject = JSON.parseObject(messageData);
            log.info("res:{}", jsonObject);
        });
    }
}
