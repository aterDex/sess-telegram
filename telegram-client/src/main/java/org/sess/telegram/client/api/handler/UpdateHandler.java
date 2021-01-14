package org.sess.telegram.client.api.handler;

import org.sess.telegram.client.api.pojo.Update;

public interface UpdateHandler {

    /**
     * MessageHandler
     * @param update message from telegram
     * @param context context for message
     * @return true - message Handler Ok, false - skip message
     */
    boolean handler(Update update, MessageHandlerContext context);
}
