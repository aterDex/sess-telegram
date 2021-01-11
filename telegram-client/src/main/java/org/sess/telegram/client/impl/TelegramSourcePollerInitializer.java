package org.sess.telegram.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@Profile("TelegramSourcePoller")
public class TelegramSourcePollerInitializer {

    private final TelegramSourcePollerSpringEventPublisher telegramSourcePollerSpringEventPublisher;

    public TelegramSourcePollerInitializer(TelegramSourcePollerSpringEventPublisher telegramSourcePollerSpringEventPublisher) {
        this.telegramSourcePollerSpringEventPublisher = telegramSourcePollerSpringEventPublisher;
    }

    @PostConstruct
    public void startPoller() {
        telegramSourcePollerSpringEventPublisher.startAndWait();
    }
}
