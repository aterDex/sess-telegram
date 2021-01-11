package org.sess.client.pojo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TelegramUser {

    String nickname;
    String email;
    City city;
    Sex sex;
    int birthday;
    long telegramId;
}
