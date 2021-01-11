package org.sess.telegram.bot.config;

import org.sess.telegram.client.api.handler.MessageHandler;
import org.sess.telegram.client.api.handler.MessageHandlerCreateException;
import org.sess.telegram.client.api.handler.MessageHandlerStore;
import org.sess.telegram.client.impl.handler.MessageHandlerStoreAbstract;
import org.sess.telegram.client.api.TelegramTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHandlerStoreConfig {

    @Bean
    public MessageHandlerStore messageHandlerStore(ApplicationContext applicationContext, @Qualifier("defaultMessageHandler") MessageHandler defaultMessageHandler, TelegramTemplate telegramTemplate) {
        return new MessageHandlerStoreAbstract(defaultMessageHandler, telegramTemplate) {
            @Override
            public MessageHandler getMessageHandler(String name) {
                try {
                    return applicationContext.getBean(name, MessageHandler.class);
                } catch (Exception be) {
                    throw new MessageHandlerCreateException(be);
                }
            }
        };
    }
}
