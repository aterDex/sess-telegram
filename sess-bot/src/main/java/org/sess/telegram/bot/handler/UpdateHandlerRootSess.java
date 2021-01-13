package org.sess.telegram.bot.handler;

import org.sess.client.api.SessTemplate;
import org.sess.telegram.bot.MessageTextKey;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.impl.TelegramMessageUtils;

import java.util.Map;

public class UpdateHandlerRootSess implements UpdateHandler {

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;
    private final Map<String, String> menu;

    public UpdateHandlerRootSess(SessTemplate sessTemplate, MessageTextResolver messageTextResolver, Map<String, String> menu) {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
        this.menu = menu;
    }

    @Override
    public boolean handler(Update update, MessageHandlerContext context) {
        if (update.getMessage() != null) {
            if (sessTemplate.isRegisterUser(update.getMessage().getChat().getId())) {
                return handlerMenu(update, context);
            }
            return handlerNewUser(update, context);
        }
        return false;
    }

    private boolean handlerMenu(Update update, MessageHandlerContext context) {
        var handlerName = menu.get(update.getMessage().getText());
        if (handlerName == null) {
            context.getTelegramTemplate().sendMessage(
                    TelegramMessageUtils.createAnswer(update.getMessage(),
                            messageTextResolver.resolveTextById(update.getMessage().getFrom().getLanguage_code(), MessageTextKey.UNKNOWN_COMMAND))
            );
        } else {
            context.getUpdateHandlerStore().addLastHandler(update.getMessage().getChat().getId(),
                    context.getUpdateHandlerFactoryStore().getHandler(handlerName));
            context.getUpdateHandlerStore().handler(update);
        }
        return true;
    }

    private boolean handlerNewUser(Update update, MessageHandlerContext context) {
        context.getUpdateHandlerStore().addLastHandler(update.getMessage().getChat().getId(),
                context.getUpdateHandlerFactoryStore().getHandler("updateHandlerNewUser"));
        context.getUpdateHandlerStore().handler(update);
        return true;
    }
}
