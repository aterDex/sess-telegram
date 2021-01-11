package org.sess.telegram.bot.handler;

import org.sess.client.api.SessTemplate;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.impl.TelegramMessageUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("defaultUpdateHandler")
public class UpdateHandlerRootSess implements UpdateHandler {

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;

    public UpdateHandlerRootSess(SessTemplate sessTemplate, MessageTextResolver messageTextResolver) {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
    }

    @Override
    public boolean handler(Update update, MessageHandlerContext context) {
        if (update.getMessage() != null) {
            if (sessTemplate.isRegisterUser(update.getMessage().getChat().getId())) {
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(update.getMessage(),
                                messageTextResolver.resolveTextById(update.getMessage().getFrom().getLanguage_code(), "in_progress"))
                );
            } else {
                context.getUpdateHandlerStore().addLastHandler(update.getMessage().getChat().getId(), context.getUpdateHandlerStore().getMessageHandler("updateHandlerNewUser"));
                context.getUpdateHandlerStore().handler(update);
            }
            return true;
        }
        return false;
    }
}
