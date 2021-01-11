package org.sess.telegram.bot.config;

import org.sess.client.api.SessTemplate;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.bot.handler.UpdateHandlerHelp;
import org.sess.telegram.bot.handler.UpdateHandlerRootSess;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class UpdateHandlerConfig {

    private static final Map<String, String> COMMANDS = Map.of(
            "/events", "updateHandlerSoon",
            "/about", "updateHandlerSoon",
            "/help", "updateHandlerHelp");

    @Bean
    @Qualifier("defaultUpdateHandler")
    public UpdateHandlerRootSess updateHandlerRootSess(SessTemplate sessTemplate, MessageTextResolver messageTextResolver) {
        return new UpdateHandlerRootSess(sessTemplate, messageTextResolver, COMMANDS);
    }

    @Bean
    @Qualifier("updateHandlerHelp")
    public UpdateHandlerHelp updateHandlerHelp() {
        return new UpdateHandlerHelp(COMMANDS.keySet().stream().sorted(String::compareTo).collect(Collectors.joining("\r\n")));
    }
}
