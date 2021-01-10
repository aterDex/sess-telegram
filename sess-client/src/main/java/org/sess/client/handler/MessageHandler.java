package org.sess.client.handler;

import org.sess.telegram.client.api.pojo.Message;

public interface MessageHandler {

    /**
     * MessageHandler
     * @param msg message from telegram
     * @param context context for message
     * @return true - message Handler Ok, false - skip message
     */
    boolean handler(Message msg, MessageHandlerContext context);
}
