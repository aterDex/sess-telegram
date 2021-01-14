package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class CallbackQuery {

    private String id;
    private User user;
    private Message message;
    private String inline_message_id;
    private String chat_instance;
    private String data;
    private String game_short_name;
}
