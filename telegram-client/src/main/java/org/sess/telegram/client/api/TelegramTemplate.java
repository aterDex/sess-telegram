package org.sess.telegram.client.api;

import org.sess.telegram.client.api.pojo.Message;
import org.sess.telegram.client.api.pojo.MessageOut;

public interface TelegramTemplate {

    boolean check();

    Message sendMessage(MessageOut message);
}
