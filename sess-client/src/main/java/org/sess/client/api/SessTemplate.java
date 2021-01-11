package org.sess.client.api;

import org.sess.client.pojo.TelegramUser;

public interface SessTemplate {

    void createUser(TelegramUser user);

    boolean isRegisterUser(long telegramId);
}
