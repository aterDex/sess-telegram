package org.sess.client;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.MessageAsSpringEvent;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.pojo.MessageOut;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Echo implements ApplicationListener<MessageAsSpringEvent> {

    private final TelegramTemplate telegramTemplate;

    public Echo(TelegramTemplate telegramTemplate) {
        this.telegramTemplate = telegramTemplate;
    }

    @Override
    public void onApplicationEvent(MessageAsSpringEvent event) {
        telegramTemplate.sendMessage(MessageOut.builder()
                .chat_id(String.valueOf(event.getUpdate().getMessage().getChat().getId()))
                .text("Echo: " + event.getUpdate().getMessage().getText())
                .build()
        );
    }
}
