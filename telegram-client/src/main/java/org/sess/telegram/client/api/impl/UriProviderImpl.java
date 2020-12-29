package org.sess.telegram.client.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class UriProviderImpl implements UriProvider {

    private final String botId;

    public UriProviderImpl(@Value("${telegram.bot.id}") String botId) {
        this.botId = botId;
    }

    @Override
    public URI createUri(String method) {
        String uriTemplate = "https://api.telegram.org/bot{botId}/{method}";
        return UriComponentsBuilder.fromUriString(uriTemplate).build(Map.of("botId", botId, "method", method));
    }
}
