package org.sess.client.pojo;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TelegramUser {

    private String nickname;
    private String email;
    private City city;
    private Sex sex;
    private LocalDateTime birthday;
    private long telegramId;
}
