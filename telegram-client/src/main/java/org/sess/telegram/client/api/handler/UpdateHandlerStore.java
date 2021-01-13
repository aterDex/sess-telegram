package org.sess.telegram.client.api.handler;

import org.sess.telegram.client.api.pojo.Update;

public interface UpdateHandlerStore {

    void addLastHandler(long chatId, UpdateHandler mh);

    void removeLastHandler(long chatId);

    void handler(Update msg);
}
