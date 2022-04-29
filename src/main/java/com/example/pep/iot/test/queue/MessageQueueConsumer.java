package com.example.pep.iot.test.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author LiuGang
 * @since 2022-04-23 17:10
 */
@Slf4j
@Component
public class MessageQueueConsumer {

    @EventListener(classes = {MessageEvent.class})
    public void consumer(MessageEvent messageEvent) {
        String data = messageEvent.getData();
        log.info("consumer data:{}", data);
    }
}
