package org.sess.telegram.client.api.handler;

import org.sess.telegram.client.api.pojo.Message;

public interface MessageHandlerStore {

    void addLastHandler(long chatId, MessageHandler mh);

    void removeLastHandler(long chatId);

    MessageHandler getMessageHandler(String name);

    void handler(Message msg);
}
