package org.sess.client;

import lombok.extern.slf4j.Slf4j;
import org.sess.client.handler.MessageHandlerStore;
import org.sess.telegram.client.api.MessageAsSpringEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener implements ApplicationListener<MessageAsSpringEvent> {

    private final MessageHandlerStore messageHandlerStore;

    public MessageListener(MessageHandlerStore messageHandlerStore) {
        this.messageHandlerStore = messageHandlerStore;
    }

    @Override
    public void onApplicationEvent(MessageAsSpringEvent event) {
        log.info("--- {}", event.getUpdate());
        messageHandlerStore.handler(event.getUpdate().getMessage());
    }
}
