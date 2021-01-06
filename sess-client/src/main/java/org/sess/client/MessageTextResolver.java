package org.sess.client;

public interface MessageTextResolver {

    String resolveTextById(String locale, String textId);

    String resolveTextById(String locale, String textId, Object... param);
}
