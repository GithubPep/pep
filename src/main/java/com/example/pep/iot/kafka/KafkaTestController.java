package com.example.pep.iot.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.pep.iot.response.ResultVO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka测试控制器
 *
 * @author LiuGang
 * @since 2022-03-13 10:13 PM
 */
@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private KafkaTestController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    private ResultVO<Object> send(@RequestBody JSONObject jsonObject) {
        kafkaTemplate.send("first-topic", jsonObject.toJSONString());
        return ResultVO.ok();
    }
}
