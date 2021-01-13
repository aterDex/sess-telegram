package org.sess.telegram.client.impl.handler;

import org.sess.telegram.client.api.handler.MessageHandlerCreateException;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.handler.UpdateHandlerFactory;
import org.sess.telegram.client.api.handler.UpdateHandlerFactoryStore;

import java.util.HashMap;
import java.util.Map;

public class UpdateHandlerFactoryStoreByMap implements UpdateHandlerFactoryStore {

    private final Map<String, UpdateHandlerFactory<?>> factoryMap;

    public UpdateHandlerFactoryStoreByMap(Map<String, UpdateHandlerFactory<?>> factoryMap) {
        this.factoryMap = new HashMap<>(factoryMap);
    }

    @Override
    public UpdateHandler getHandler(String name) {
        var updateHandler = factoryMap.get(name);
        if (updateHandler == null) {
            throw new MessageHandlerCreateException("Not found handler '" + name + "'");
        }
        return updateHandler.getHandler();
    }
}
