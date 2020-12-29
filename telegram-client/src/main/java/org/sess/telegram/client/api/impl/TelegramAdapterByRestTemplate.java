package org.sess.telegram.client.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class TelegramAdapterByRestTemplate implements TelegramAdapter {

    private final String botId;
    private final RestTemplate botServer;

    public TelegramAdapterByRestTemplate(@Value("${telegram.bot.id}") String botId, RestTemplate botServer) {
        this.botId = botId;
        this.botServer = botServer;
    }

    @Override
    public boolean check() {
        try {
            log.debug("invoke: check");
            return botServer.postForEntity("https://api.telegram.org/bot{botId}/getMe", null, String.class, Map.of("botId", botId))
                    .getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }
}
