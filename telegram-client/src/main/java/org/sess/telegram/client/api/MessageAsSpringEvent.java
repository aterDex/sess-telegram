package org.sess.telegram.client.api;

import org.sess.telegram.client.api.pojo.Update;
import org.springframework.context.ApplicationEvent;

public class MessageAsSpringEvent extends ApplicationEvent {
    private final Update update;

    public MessageAsSpringEvent(Object source, Update update) {
        super(source);
        this.update = update;
    }

    public Update getUpdate() {
        return update;
    }
}
