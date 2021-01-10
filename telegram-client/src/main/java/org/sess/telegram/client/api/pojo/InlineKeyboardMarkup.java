package org.sess.telegram.client.api.pojo;

import lombok.Data;

import java.util.List;

@Data
public class InlineKeyboardMarkup {

    private List<List<InlineKeyboardButton>> inline_keyboard;
}
