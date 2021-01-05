package org.sess.telegram.client.api.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.MessageAsSpringEvent;
import org.sess.telegram.client.api.TelegramSource;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.api.pojo.UpdateRequest;
import org.sess.telegram.client.api.pojo.UpdateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Profile("TelegramSourcePoller")
public class TelegramSourcePollerSpringEventPublisher implements TelegramSource {

    private final RestTemplate botServer;
    private final UriProvider telegramUriProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Getter
    @Setter
    @Value("${telegram.bot.poller.timeout}")
    private int timeout;

    public TelegramSourcePollerSpringEventPublisher(UriProvider telegramUriProvider,
                                                    RestTemplate botServer,
                                                    ApplicationEventPublisher applicationEventPublisher) {
        this.botServer = botServer;
        this.telegramUriProvider = telegramUriProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    public void startAndWait() {
        log.debug("start");
        Integer offset = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                offset = process(nextUpdate(offset), offset);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("stop");
    }

    private Integer process(ResponseEntity<UpdateResponse> response, Integer lastOffset) {
        if (response.getStatusCode() == HttpStatus.OK &&
                response.getBody() != null &&
                response.getBody().isOk() &&
                response.getBody().getResult() != null) {
            var body = response.getBody();
            body.getResult().stream()
                    .map(x -> new MessageAsSpringEvent(this, x))
                    .forEach(applicationEventPublisher::publishEvent);

            return body.getResult().stream()
                    .map(Update::getUpdate_id)
                    .max(Integer::compareTo)
                    .map(x -> x + 1).orElse(lastOffset);
        }
        log.error("getUpdates isn't gotten - {}", response);
        return lastOffset;
    }

    private ResponseEntity<UpdateResponse> nextUpdate(final Integer offset) {
        var response = botServer.postForEntity(
                telegramUriProvider.createUri("getUpdates"),
                new UpdateRequest(timeout, offset),
                UpdateResponse.class);
        log.trace("{}", response);
        return response;
    }
}