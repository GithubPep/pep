package com.example.pep.iot.test.queue;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuGang
 * @since 2022-04-23 11:56
 */
@Slf4j
@Component
public class BlockQueue implements InitializingBean, ApplicationContextAware {

    @Value("${config.queue.delay.publish.async:false}")
    private boolean asyncPublicEvent;

    @Value("${spring.application.name:}")
    private String applicationName;

    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private ApplicationContext applicationContext;

    //定时的key失效后,到达该丢列,然后取出来,消费即可
    private RBlockingDeque<String> rBlockingDeque;

    //redisson提供的延时队列 底层采用zSet
    private RDelayedQueue<String> rDelayedQueue;

    /**
     * 发送消息到延时队列
     *
     * @param t        消息体
     * @param time     时间
     * @param timeUnit 单位
     */
    public void send(String t, long time, TimeUnit timeUnit) {
        Assert.notNull(t, "send object is null");
        //将消息放到延时队列中
        this.rDelayedQueue.offer(t, time, timeUnit);
        log.info("send delay [{}], timeUnit[{}] jsonObject[{}]", time, timeUnit, t);
    }


    /**
     * bean创建后,一直进行消费
     */
    @Override
    public void afterPropertiesSet() {
        String queueName = this.applicationName;
        this.rBlockingDeque = this.redissonClient.getBlockingDeque(queueName);
        if (this.rBlockingDeque != null) {
            this.rDelayedQueue = this.redissonClient.getDelayedQueue(this.rBlockingDeque);
            if (this.rDelayedQueue != null) {
                //持续消费
                this.startConsumerDelayQueue();
            }
        }
    }

    private void startConsumerDelayQueue() {
        //守护线程,一直从队列中取消息,然后封装成事件,发布出去
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String take = rBlockingDeque.take();
                    MessageEvent messageEvent = new MessageEvent(take);
                    //如果开启异步的话
                    if (asyncPublicEvent && Objects.nonNull(taskExecutor)) {
                        taskExecutor.execute(() -> {
                            log.info("publish event {}", messageEvent);
                            applicationContext.publishEvent(messageEvent);
                        });
                    } else {
                        log.info("publish event {}", messageEvent);
                        applicationContext.publishEvent(messageEvent);
                    }
                } catch (InterruptedException var) {
                    log.error(var.getMessage());
                }
            }
        }, "redisson-queue-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean sendIfAbsent(String t, long time, TimeUnit timeUnit) {
        if (this.contains(t)) {
            return false;
        } else {
            this.send(t, time, timeUnit);
            return true;
        }
    }

    public boolean remove(String t) {
        //从延时队列中删除
        rBlockingDeque.remove(t);
        return this.rDelayedQueue.remove(t);
    }

    public boolean contains(String t) {
        return this.rDelayedQueue.contains(t);
    }
}
