package org.sess.telegram.bot;

public interface MessageTextResolver {

    String resolveTextById(String locale, MessageTextKey textId);

    String resolveTextById(String locale, MessageTextKey textId, Object... param);
}
