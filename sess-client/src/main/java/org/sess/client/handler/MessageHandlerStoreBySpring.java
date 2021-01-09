package org.sess.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.pojo.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Slf4j
@Service
public class MessageHandlerStoreBySpring implements MessageHandlerStore {

    private final Map<Long, Deque<MessageHandler>> store = new HashMap<>();
    private final MessageHandler defaultMessageHandler;
    private final TelegramTemplate telegramTemplate;
    private final ApplicationContext applicationContext;

    public MessageHandlerStoreBySpring(@Qualifier("defaultMessageHandler") MessageHandler defaultMessageHandler, TelegramTemplate telegramTemplate, ApplicationContext applicationContext) {
        this.defaultMessageHandler = defaultMessageHandler;
        this.telegramTemplate = telegramTemplate;
        this.applicationContext = applicationContext;
    }

    @Override
    public void addLastHandler(long chatId, MessageHandler mh) {
        store.computeIfAbsent(chatId, x -> new LinkedList<>())
                .addLast(mh);
    }

    @Override
    public void removeLastHandler(long chatId) {
        var chainHandler = store.get(chatId);
        if (chainHandler != null) {
            chainHandler.pollLast();
        }
    }

    @Override
    public MessageHandler getMessageHandler(String name) throws MessageHandlerCreateException {
        try {
            return applicationContext.getBean(name, MessageHandler.class);
        } catch (Exception be) {
            throw new MessageHandlerCreateException(be);
        }
    }

    @Override
    public void handler(Message msg) {
        getLastHandler(msg)
                .handler(msg, new MessageHandlerContext(telegramTemplate, this));
    }

    private MessageHandler getLastHandler(Message msg) {
        var chainHandler = store.get(msg.getChat().getId());
        if (chainHandler == null || chainHandler.isEmpty()) {
            return defaultMessageHandler;
        }
        return chainHandler.getLast();
    }
}
