package com.example.pep.iot.emq.consistent;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public enum TopicEnum {

    // device topics
    DEVICE_PUB_TOPIC("/topic/pub/device/{0}/{1}"),
    DEVICE_SUB_TOPIC("/topic/sub/device/{0}/{1}");

    private final String topic;

    TopicEnum(String topic) {
        this.topic = topic;
    }

    public static String getTopicFuzzyMatching(TopicEnum topicEnum) {
        return MessageFormat.format(topicEnum.getTopic(), "*", "*");
    }

    public static String getTopicExact(TopicEnum topicEnum, String brandCode, String clientId) {
        return MessageFormat.format(topicEnum.getTopic(), brandCode, clientId);
    }

    public static String getEdgeLinkTopicExact(TopicEnum topicEnum, String key) {
        return MessageFormat.format(topicEnum.getTopic(), key);
    }
    }
