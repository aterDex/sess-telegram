package org.sess.client.config;

import org.sess.client.handler.MessageHandler;
import org.sess.client.handler.MessageHandlerCreateException;
import org.sess.client.handler.MessageHandlerStore;
import org.sess.client.handler.MessageHandlerStoreAbstract;
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
