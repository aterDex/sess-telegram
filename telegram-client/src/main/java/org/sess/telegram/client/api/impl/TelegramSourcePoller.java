package org.sess.telegram.client.api.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramSource;
import org.sess.telegram.client.api.Update;
import org.sess.telegram.client.api.UpdateRequest;
import org.sess.telegram.client.api.UpdateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Profile("TelegramSourcePoller")
public class TelegramSourcePoller implements TelegramSource {

    private final RestTemplate botServer;
    private final UriProvider telegramUriProvider;

    @Getter
    @Setter
    @Value("${telegram.bot.poller.timeout}")
    private int timeout;

    public TelegramSourcePoller(UriProvider telegramUriProvider, RestTemplate botServer) {
        this.botServer = botServer;
        this.telegramUriProvider = telegramUriProvider;
    }

    @Async
    public void startAndWait() {
        log.debug("start");
        Integer offset = null;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ResponseEntity<UpdateResponse> res = botServer.postForEntity(
                        telegramUriProvider.createUri("getUpdates"),
                        new UpdateRequest(timeout, offset),
                        UpdateResponse.class);
                if (res.getStatusCode() == HttpStatus.OK &&
                        res.getBody() != null &&
                        res.getBody().isOk() &&
                        res.getBody().getResult() != null) {

                    UpdateResponse response = res.getBody();
                    offset = response.getResult().stream().map(Update::getUpdate_id).max(Integer::compareTo).map(x -> x + 1).orElse(offset);
                    log.trace("{}", response);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("stop");
    }
}