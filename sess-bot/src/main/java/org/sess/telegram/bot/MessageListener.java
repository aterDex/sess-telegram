package org.sess.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.impl.MessageAsSpringEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener implements ApplicationListener<MessageAsSpringEvent> {

    private final UpdateHandlerStore updateHandlerStore;

    public MessageListener(UpdateHandlerStore updateHandlerStore) {
        this.updateHandlerStore = updateHandlerStore;
    }

    @Override
    public void onApplicationEvent(MessageAsSpringEvent event) {
        log.info("--- {}", event.getUpdate());
        try {
            updateHandlerStore.handler(event.getUpdate());
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
