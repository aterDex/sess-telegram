package org.sess.telegram.client.api.handler;

import lombok.Value;
import org.sess.telegram.client.api.TelegramTemplate;

@Value
public class MessageHandlerContext {

    TelegramTemplate telegramTemplate;
    MessageHandlerStore messageHandlerStore;
}
