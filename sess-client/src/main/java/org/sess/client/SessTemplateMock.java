package org.sess.client;

import org.sess.client.pojo.TelegramUser;
import org.springframework.stereotype.Service;

@Service
public class SessTemplateMock implements SessTemplate {


    @Override
    public void createUser(TelegramUser user) {
    }

    @Override
    public boolean isRegisterUser(long telegramId) {
        return telegramId != 509384962;
    }
}
