package org.sess.telegram.client.impl.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.handler.MessageHandler;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.MessageHandlerStore;
import org.sess.telegram.client.api.pojo.Message;

import java.util.*;

@Slf4j
public abstract class MessageHandlerStoreAbstract implements MessageHandlerStore {

    private final Map<Long, Deque<MessageHandler>> store = new HashMap<>();
    private final MessageHandler defaultMessageHandler;
    private final TelegramTemplate telegramTemplate;

    public MessageHandlerStoreAbstract(MessageHandler defaultMessageHandler, TelegramTemplate telegramTemplate) {
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
        Iterator<MessageHandler> iterator = getHandlerChain(msg).descendingIterator();
        while (iterator.hasNext()) {
            MessageHandler messageHandler = iterator.next();
            if (messageHandler.handler(msg,
                    new MessageHandlerContext(telegramTemplate, this))) {
                break;
            }
        }
    }

    private Deque<MessageHandler> getHandlerChain(Message msg) {
        var chainHandler = store.get(msg.getChat().getId());
        if (chainHandler == null || chainHandler.isEmpty()) {
            return new ArrayDeque<>(Collections.singletonList(defaultMessageHandler));
        }
        return chainHandler;
    }
}
