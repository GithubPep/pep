package com.example.pep.iot;

import com.example.pep.iot.statemachine.RefundReasonEvents;
import com.example.pep.iot.statemachine.RefundReasonStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import javax.annotation.Resource;

/**
 * @author LiuGang
 * @since 2022-03-09 4:34 PM
 */
@Slf4j
@SpringBootTest
public class StateMachineTest {

    @Resource
    private StateMachine<RefundReasonStatus, RefundReasonEvents> stateMachine;

    @Test
    public void testState() {
        stateMachine.start();

        PepIotApplicationTests.Address address = new PepIotApplicationTests.Address()
                .setCity("上海浦东新区").setCountry("中国");

        PepIotApplicationTests.User user = new PepIotApplicationTests.User()
                .setName("zhangSan")
                .setAge(25)
                .setAddress(address);

        Message<RefundReasonEvents> eventsMessage = MessageBuilder
                .withPayload(RefundReasonEvents.APPROVE)
                .setHeader("info", user)
                .build();
        stateMachine.sendEvent(eventsMessage);
    }

}
