package org.sess.client;

import lombok.Lombok;
import org.sess.client.pojo.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SessTemplateMock implements SessTemplate {

    private Set<Long> users = new HashSet<>();

    @Override
    public void createUser(TelegramUser user) {
        users.add(user.getTelegramId());
    }

    @Override
    public boolean isRegisterUser(long telegramId) {
        return users.contains(telegramId);
    }
}
