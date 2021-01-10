package org.sess.client.handler;

import org.sess.client.MessageTextResolver;
import org.sess.client.SessTemplate;
import org.sess.telegram.client.api.pojo.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("defaultMessageHandler")
public class MessageHandlerRootSess implements MessageHandler {

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;

    public MessageHandlerRootSess(SessTemplate sessTemplate, MessageTextResolver messageTextResolver) {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
    }

    @Override
    public boolean handler(Message msg, MessageHandlerContext context) {
        if (sessTemplate.isRegisterUser(msg.getChat().getId())) {
            context.getTelegramTemplate().sendMessage(
                    TelegramMessageUtils.createAnswer(msg,
                            messageTextResolver.resolveTextById(msg.getFrom().getLanguage_code(), "in_progress"))
            );
        } else {
            context.getMessageHandlerStore().addLastHandler(msg.getChat().getId(), context.getMessageHandlerStore().getMessageHandler("messageHandlerNewUser"));
            context.getMessageHandlerStore().handler(msg);
        }
        return true;
    }
}
