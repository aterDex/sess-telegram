package org.sess.client.handler;

import org.sess.telegram.client.api.pojo.Message;

public interface MessageHandlerStore {

    void addLastHandler(long chatId, MessageHandler mh);

    void removeLastHandler(long chatId);

    void handler(Message msg);
}
