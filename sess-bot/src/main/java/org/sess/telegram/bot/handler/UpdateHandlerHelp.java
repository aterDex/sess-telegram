package org.sess.telegram.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.impl.TelegramMessageUtils;

@Slf4j
public class UpdateHandlerHelp implements UpdateHandler {

    private final String help;

    public UpdateHandlerHelp(String help) {
        this.help = help;
    }

    @Override
    public boolean handler(Update update, MessageHandlerContext context) {
        if (update.getMessage() != null) {
            context.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(update.getMessage(), help));
            context.getUpdateHandlerStore().removeLastHandler(update.getMessage().getChat().getId());
        }
        return true;
    }
}
