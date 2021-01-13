package org.sess.telegram.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramSource;
import org.sess.telegram.client.api.UriProvider;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.api.pojo.UpdateRequest;
import org.sess.telegram.client.api.pojo.UpdateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@ConditionalOnProperty(
        value = "telegram.bot.poller.timeout",
        matchIfMissing = false)
public class TelegramSourcePoller implements TelegramSource {

    private final RestTemplate botServer;
    private final UriProvider telegramUriProvider;
    private final UpdateHandlerStore updateHandlerStore;
    private final int timeout;

    public TelegramSourcePoller(UriProvider telegramUriProvider,
                                RestTemplate botServer,
                                UpdateHandlerStore updateHandlerStore,
                                @Value("${telegram.bot.poller.timeout}") int timeout) {
        this.botServer = botServer;
        this.telegramUriProvider = telegramUriProvider;
        this.updateHandlerStore = updateHandlerStore;
        this.timeout = timeout;
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
            body.getResult().forEach(updateHandlerStore::handler);

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