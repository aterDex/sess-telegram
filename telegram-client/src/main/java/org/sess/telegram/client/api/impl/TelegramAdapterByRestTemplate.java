package org.sess.telegram.client.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TelegramAdapterByRestTemplate implements TelegramAdapter {

    private final UriProvider telegramUriProvider;
    private final RestTemplate botServer;

    public TelegramAdapterByRestTemplate(UriProvider telegramUriProvider, RestTemplate botServer) {
        this.telegramUriProvider = telegramUriProvider;
        this.botServer = botServer;
    }

    @Override
    public boolean check() {
        try {
            log.debug("invoke: check");
            return botServer.postForEntity(telegramUriProvider.createUri("getMe"), null, String.class)
                    .getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }
}
