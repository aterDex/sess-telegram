package org.sess.client;

import org.sess.client.pojo.TelegramUser;

public interface SessTemplate {

    void createUser(TelegramUser user);

    boolean isRegisterUser(long telegramId);
}
