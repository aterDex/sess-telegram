package org.sess.telegram.client.api.handler;

public interface UpdateHandlerFactory<T extends UpdateHandler> {

    String getName();
    T getHandler();
}
