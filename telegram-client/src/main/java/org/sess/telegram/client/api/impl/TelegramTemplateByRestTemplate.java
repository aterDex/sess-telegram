package org.sess.telegram.client.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.pojo.Message;
import org.sess.telegram.client.api.pojo.MessageOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TelegramTemplateByRestTemplate implements TelegramTemplate {

    private final UriProvider telegramUriProvider;
    private final RestTemplate botServer;

    public TelegramTemplateByRestTemplate(UriProvider telegramUriProvider, RestTemplate botServer) {
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

    @Override
    public Message sendMessage(MessageOut message) {
        ResponseEntity<Message> response = botServer.postForEntity(telegramUriProvider.createUri("sendMessage"), message, Message.class);
        return response.getBody();
    }
}
