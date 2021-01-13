package org.sess.telegram.bot.handler;

import org.sess.telegram.client.api.handler.UpdateHandlerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateHandlerHelpFactoryStore implements UpdateHandlerFactory<UpdateHandlerHelp> {

    private final UpdateHandlerHelp updateHandlerHelp;

    public UpdateHandlerHelpFactoryStore(UpdateHandlerHelp updateHandlerHelp) {
        this.updateHandlerHelp = updateHandlerHelp;
    }

    public String getName() {
        return "updateHandlerHelp";
    }

    public UpdateHandlerHelp getHandler() {
        return updateHandlerHelp;
    }
}
