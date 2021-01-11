package org.sess.telegram.bot;

public interface MessageTextResolver {

    String resolveTextById(String locale, String textId);

    String resolveTextById(String locale, String textId, Object... param);
}
