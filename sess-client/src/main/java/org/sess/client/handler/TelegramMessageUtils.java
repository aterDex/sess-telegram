package org.sess.client.handler;

import org.sess.telegram.client.api.pojo.*;

import java.util.Arrays;
import java.util.Collections;

public class TelegramMessageUtils {

    private TelegramMessageUtils() {
    }

    public static MessageOut createAnswer(Message msg, String text) {
        return MessageOut.builder()
                .chat_id(String.valueOf(msg.getChat().getId()))
                .text(text)
                .reply_markup(new ReplyKeyboardRemove(true, null))
                .build();
    }

    public static MessageOut createAnswer(Message msg, String text, ReplyKeyboardMarkup keyboard) {
        return MessageOut.builder()
                .chat_id(String.valueOf(msg.getChat().getId()))
                .text(text)
                .reply_markup(keyboard)
                .build();
    }

    /**
     * Create Keyboard in one line
     * @param buttons buttons
     * @return keyboard
     */
    public static ReplyKeyboardMarkup createKeyBoard(boolean oneTimeKeyboard, KeyboardButton... buttons) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(Arrays.asList(buttons)))
                .one_time_keyboard(oneTimeKeyboard)
                .build();
    }
}
