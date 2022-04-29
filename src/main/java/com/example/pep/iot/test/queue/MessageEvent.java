package com.example.pep.iot.test.queue;

import org.springframework.context.ApplicationEvent;

/**
 * @author LiuGang
 * @since 2022-04-23 12:03
 */
public class MessageEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7619466969906544497L;

    private final String data;

    public MessageEvent(String data) {
        super("");
        this.data = data;
    }

    public MessageEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return "MessageEvent{data=" + this.data + '}';
    }
}
