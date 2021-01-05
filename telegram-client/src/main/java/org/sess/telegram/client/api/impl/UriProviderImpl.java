package org.sess.telegram.client.api.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class UriProviderImpl implements UriProvider {

    private final String botId;
    private final String template;

    public UriProviderImpl(@Value("${telegram.bot.id}") String botId, @Value("${telegram.bot.template}") String template) {
        this.botId = botId;
        this.template = template;
    }

    @Override
    public URI createUri(String method) {
        return UriComponentsBuilder.fromUriString(template).build(Map.of("botId", botId, "method", method));
    }
}
