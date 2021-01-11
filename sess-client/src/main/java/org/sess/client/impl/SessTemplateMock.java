package org.sess.client.impl;

import org.sess.client.api.SessTemplate;
import org.sess.client.pojo.TelegramUser;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("WithMockSessServices")
public class SessTemplateMock implements SessTemplate {

    private final Set<Long> users = new HashSet<>();

    @Override
    public void createUser(TelegramUser user) {
        users.add(user.getTelegramId());
    }

    @Override
    public boolean isRegisterUser(long telegramId) {
        return users.contains(telegramId);
    }
}
