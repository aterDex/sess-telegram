package org.sess.telegram.client.impl.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.handler.UpdateHandlerStore;
import org.sess.telegram.client.api.pojo.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class UpdateHandlerStoreWithTimeCheckAbstract implements UpdateHandlerStore {

    private final Map<Long, Deque<UpdateHandler>> store = new ConcurrentHashMap<>();
    private final Map<Long, LocalDateTime> lastWork = new ConcurrentHashMap<>();

    private final UpdateHandler defaultUpdateHandler;
    private final TelegramTemplate telegramTemplate;
    private final Deque<UpdateHandler> defaultUpdateHandlerDeque;

    @Getter
    @Setter
    @Value("${telegram.bot.session.timeout}")
    private int timeoutSession;

    public UpdateHandlerStoreWithTimeCheckAbstract(UpdateHandler defaultUpdateHandler, TelegramTemplate telegramTemplate) {
        this.defaultUpdateHandler = defaultUpdateHandler;
        this.telegramTemplate = telegramTemplate;
        this.defaultUpdateHandlerDeque = new ArrayDeque<>(Collections.singletonList(defaultUpdateHandler));
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

    @Scheduled(fixedRateString = "${telegram.bot.session.period}")
    public void check() {
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<Long, LocalDateTime> entry : lastWork.entrySet()) {
            if (entry.getValue().until(now, ChronoUnit.MILLIS) > timeoutSession) {
                discharge(entry.getKey());
            }
        }
    }

    private Deque<UpdateHandler> findHandlerChain(Update update) {
        if (update.getMessage() == null) {
            throw new UnsupportedOperationException("Now it unsupported for not message update");
        }
        long chatId = update.getMessage().getChat().getId();
        var chainHandler = store.get(chatId);
        if (chainHandler == null || chainHandler.isEmpty()) {
            return defaultUpdateHandlerDeque;
        }
        updateLastWork(chatId);
        return chainHandler;
    }

    private void updateLastWork(long chatId) {
        lastWork.put(chatId, LocalDateTime.now());
    }

    private void discharge(Long chatId) {
        store.remove(chatId);
        lastWork.remove(chatId);
    }
}
