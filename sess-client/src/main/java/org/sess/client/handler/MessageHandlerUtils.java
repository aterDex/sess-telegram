package org.sess.client.handler;

import org.sess.telegram.client.api.TelegramTemplate;
import org.sess.telegram.client.api.pojo.Message;
import org.sess.telegram.client.api.pojo.MessageOut;

public class MessageHandlerUtils {

    private MessageHandlerUtils() {}

    public static void sendText(Message msg, TelegramTemplate template, String text) {
        template.sendMessage(MessageOut.builder()
                .chat_id(String.valueOf(msg.getChat().getId()))
                .text(text)
                .build());
    }
}
