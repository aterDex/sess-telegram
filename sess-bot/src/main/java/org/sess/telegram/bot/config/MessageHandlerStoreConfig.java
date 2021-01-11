package org.sess.telegram.bot.config;

import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.handler.MessageHandlerCreateException;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.impl.handler.UpdateHandlerStoreAbstract;
import org.sess.telegram.client.api.TelegramTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHandlerStoreConfig {

    @Bean
    public UpdateHandlerStore messageHandlerStore(ApplicationContext applicationContext, @Qualifier("defaultUpdateHandler") UpdateHandler defaultUpdateHandler, TelegramTemplate telegramTemplate) {
        return new UpdateHandlerStoreAbstract(defaultUpdateHandler, telegramTemplate) {
            @Override
            public UpdateHandler getMessageHandler(String name) {
                try {
                    return applicationContext.getBean(name, UpdateHandler.class);
                } catch (Exception be) {
                    throw new MessageHandlerCreateException(be);
                }
            }
        };
    }
}
