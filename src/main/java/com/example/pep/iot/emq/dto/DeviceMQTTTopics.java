package com.example.pep.iot.emq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 发布订阅topic
 *
 * @author LiuGang
 * @since 2022-02-17 11:28 AM
 */
@Data
@Accessors(chain = true)
public class DeviceMQTTTopics {

    private List<String> pubTopics;

    private List<String> subTopics;
}
