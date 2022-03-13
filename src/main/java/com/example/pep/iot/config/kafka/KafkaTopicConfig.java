package com.example.pep.iot.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kafka topic配置
 *
 * @author LiuGang
 * @since 2022-03-13 9:22 PM
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic FirstTopic() {
        return new NewTopic("first-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic SecondTopic() {
        return new NewTopic("second-topic", 1, (short) 1);
    }

}
