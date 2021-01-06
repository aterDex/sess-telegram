package org.sess.client.handler;

import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.pojo.Message;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageHandlerStoreSess implements MessageHandlerStore {

    private final Map<Long, Deque<MessageHandler>> store = new HashMap<>();
    private final MessageHandler defaultMessageHandler;
    private final TelegramTemplate telegramTemplate;

    public MessageHandlerStoreSess(MessageHandler defaultMessageHandler, TelegramTemplate telegramTemplate) {
        this.defaultMessageHandler = defaultMessageHandler;
        this.telegramTemplate = telegramTemplate;
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
