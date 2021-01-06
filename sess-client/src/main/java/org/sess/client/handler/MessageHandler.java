package org.sess.client.handler;

import org.sess.telegram.client.api.pojo.Message;

public interface MessageHandler {

    void handler(Message msg, MessageHandlerContext context);
}
