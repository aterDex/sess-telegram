package org.sess.telegram.client.impl.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.api.pojo.Update;

import java.util.*;

@Slf4j
public abstract class UpdateHandlerStoreWithTimeCheckAbstract implements UpdateHandlerStore {

    private final Map<Long, Deque<UpdateHandler>> store = new HashMap<>();
    private final UpdateHandler defaultUpdateHandler;
    private final TelegramTemplate telegramTemplate;

    public UpdateHandlerStoreWithTimeCheckAbstract(UpdateHandler defaultUpdateHandler, TelegramTemplate telegramTemplate) {
        this.defaultUpdateHandler = defaultUpdateHandler;
        this.telegramTemplate = telegramTemplate;
    }

    @Override
    public void addLastHandler(long chatId, UpdateHandler mh) {
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
    public void handler(Update update) {
        Iterator<UpdateHandler> iterator = findHandlerChain(update).descendingIterator();
        while (iterator.hasNext()) {
            UpdateHandler updateHandler = iterator.next();
            if (updateHandler.handler(update,
                    new MessageHandlerContext(telegramTemplate, this))) {
                break;
            }
        }
    }

    private Deque<UpdateHandler> findHandlerChain(Update update) {
        if (update.getMessage() == null) {
            throw new UnsupportedOperationException("Now it unsupported for not message update");
        }
        var chainHandler = store.get(update.getMessage().getChat().getId());
        if (chainHandler == null || chainHandler.isEmpty()) {
            return new ArrayDeque<>(Collections.singletonList(defaultUpdateHandler));
        }
        return chainHandler;
    }
}
