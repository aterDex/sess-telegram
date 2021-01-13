package org.sess.telegram.bot.handler;

import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.UpdateHandlerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateHandlerSoonFactoryStore implements UpdateHandlerFactory<UpdateHandlerSoon> {

    private final MessageTextResolver messageTextResolver;

    public UpdateHandlerSoonFactoryStore(MessageTextResolver messageTextResolver) {
        this.messageTextResolver = messageTextResolver;
    }

    public String getName() {
        return "updateHandlerSoon";
    }

    public UpdateHandlerSoon getHandler() {
        return new UpdateHandlerSoon(messageTextResolver);
    }
}
