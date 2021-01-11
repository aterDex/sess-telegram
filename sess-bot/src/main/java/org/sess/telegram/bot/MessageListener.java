package org.sess.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.handler.MessageHandlerStore;
import org.sess.telegram.client.impl.MessageAsSpringEvent;
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
        if (event.getUpdate().getMessage() != null) {
            messageHandlerStore.handler(event.getUpdate().getMessage());
        }
    }
}
