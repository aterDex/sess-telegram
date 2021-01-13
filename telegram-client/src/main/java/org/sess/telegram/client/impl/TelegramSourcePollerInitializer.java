package org.sess.telegram.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@ConditionalOnBean(value =TelegramSourcePoller.class )
public class TelegramSourcePollerInitializer {

    private final TelegramSourcePoller telegramSourcePoller;

    public TelegramSourcePollerInitializer(TelegramSourcePoller telegramSourcePoller) {
        this.telegramSourcePoller = telegramSourcePoller;
    }

    @PostConstruct
    public void startPoller() {
        telegramSourcePoller.startAndWait();
    }
}
