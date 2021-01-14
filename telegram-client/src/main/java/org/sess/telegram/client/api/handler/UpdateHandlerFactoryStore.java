package org.sess.telegram.client.api.handler;

public interface UpdateHandlerFactoryStore {
    UpdateHandler getHandler(String name);
}
