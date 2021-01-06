package org.sess.client.handler;

import lombok.Value;
import org.sess.client.SessTemplate;
import org.sess.telegram.client.api.TelegramTemplate;

@Value
public class MessageHandlerContext {

    private TelegramTemplate telegramTemplate;
    private MessageHandlerStore messageHandlerStore;
}
