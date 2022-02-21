package com.example.pep.iot.emq.vo;

import com.example.pep.iot.emq.dto.DeviceMQTTConnection;
import com.example.pep.iot.emq.dto.DeviceMQTTTopics;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 注册响应
 *
 * @author LiuGang
 * @since 2022-02-17 11:21 AM
 */
@Data
@Accessors(chain = true)
public class DeviceRegisterResponse {

    private String clientId;

    private DeviceMQTTConnection deviceMQTTConnection;

    private DeviceMQTTTopics deviceMQTTTopics;

}
