package org.sess.telegram.bot.handler;

import org.sess.client.api.GeoResolver;
import org.sess.client.api.SessTemplate;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.UpdateHandlerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateHandlerNewUserFactoryStore implements UpdateHandlerFactory<UpdateHandlerNewUser> {

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;
    private final GeoResolver geoResolver;

    public UpdateHandlerNewUserFactoryStore(SessTemplate sessTemplate, MessageTextResolver messageTextResolver, GeoResolver geoResolver) {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
        this.geoResolver = geoResolver;
    }

    @Override
    public String getName() {
        return "updateHandlerNewUser";
    }

    @Override
    public UpdateHandlerNewUser getHandler() {
        return new UpdateHandlerNewUser(sessTemplate, messageTextResolver, geoResolver);
    }
}
