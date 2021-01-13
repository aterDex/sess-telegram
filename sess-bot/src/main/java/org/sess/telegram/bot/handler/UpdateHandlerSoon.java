package org.sess.telegram.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.bot.MessageTextKey;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.impl.TelegramMessageUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
public class UpdateHandlerSoon implements UpdateHandler {

    private final MessageTextResolver messageTextResolver;

    public UpdateHandlerSoon(MessageTextResolver messageTextResolver) {
        this.messageTextResolver = messageTextResolver;
    }

    @Override
    public boolean handler(Update update, MessageHandlerContext context) {
        if (update.getMessage() != null) {
            context.getTelegramTemplate().sendMessage(
                    TelegramMessageUtils.createAnswer(update.getMessage(),
                            messageTextResolver.resolveTextById(update.getMessage().getFrom().getLanguage_code(), MessageTextKey.IN_PROGRESS))
            );
            context.getUpdateHandlerStore().removeLastHandler(update.getMessage().getChat().getId());
        }
        return true;
    }
}
