package org.sess.telegram.client.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InlineKeyboardMarkup {

    private List<List<InlineKeyboardButton>> inline_keyboard;
}
