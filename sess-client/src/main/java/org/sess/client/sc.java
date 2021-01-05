package org.sess.client;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.MessageAsSpringEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class sc implements ApplicationListener<MessageAsSpringEvent> {

    @Override
    public void onApplicationEvent(MessageAsSpringEvent event) {
        log.info("--------- {}", event.getUpdate().getUpdate_id());
    }
}
