package org.sess.telegram.bot.config;

import org.sess.client.api.SessTemplate;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.bot.handler.UpdateHandlerHelp;
import org.sess.telegram.bot.handler.UpdateHandlerRootSess;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.handler.UpdateHandlerFactory;
import org.sess.telegram.client.api.handler.UpdateHandlerFactoryStore;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.impl.handler.UpdateHandlerFactoryStoreByMap;
import org.sess.telegram.client.impl.handler.UpdateHandlerStoreWithTimeCheck;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class UpdateHandlerStoreConfig {

    private static final Map<String, String> COMMANDS = Map.of(
            "/events", "updateHandlerSoon",
            "/about", "updateHandlerSoon",
            "/help", "updateHandlerHelp");

    @Value("${telegram.bot.session.timeout}")
    private int timeoutSession;

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

    @Bean
    public UpdateHandlerFactoryStore updateHandlerFactoryStore(List<UpdateHandlerFactory<?>> factories) {
        Map<String, UpdateHandlerFactory<?>> factoryMap = factories.stream().collect(Collectors.toMap(UpdateHandlerFactory::getName, x -> x));
        return new UpdateHandlerFactoryStoreByMap(factoryMap);
    }

    @Bean
    public UpdateHandlerStore messageHandlerStore(ApplicationContext applicationContext,
                                                  @Qualifier("defaultUpdateHandler") UpdateHandler defaultUpdateHandler,
                                                  TelegramTemplate telegramTemplate,
                                                  UpdateHandlerFactoryStore factoryStore) {
        return new UpdateHandlerStoreWithTimeCheck(defaultUpdateHandler, telegramTemplate, factoryStore, timeoutSession);
    }
}
