package com.example.pep.iot.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Map;

/**
 * @author LiuGang
 * @since 2022-03-07 2:42 PM
 */
@Slf4j
@Component
public class MqttEntrance implements ApplicationContextAware {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void receiveMqttMessage(String topic, String message) {
        Map<String, MqttConsumer> services = applicationContext.getBeansOfType(MqttConsumer.class);
        for (MqttConsumer consumer : services.values()) {
            if (!pathMatcher.match(consumer.getTopic(), topic)) {
                continue;
            }
            try {
                consumer.receiveMsg(topic, message);
            } catch (Exception e) {
                log.error(">>>>>> mqtt msg dispose error=" + e.getMessage(), e);
            }
        }

    }


}
