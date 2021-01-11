package org.sess.telegram.client.impl;

import org.sess.telegram.client.api.pojo.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public static MessageOut createAnswer(Message msg, String text, InlineKeyboardMarkup keyboard) {
        return MessageOut.builder()
                .chat_id(String.valueOf(msg.getChat().getId()))
                .text(text)
                .reply_markup(keyboard)
                .build();
    }

    /**
     * Create Keyboard in one line
     *
     * @param buttons buttons
     * @return keyboard
     */
    public static ReplyKeyboardMarkup createOneRowReplyKeyBoardMarkup(boolean oneTimeKeyboard, KeyboardButton... buttons) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(Arrays.asList(buttons)))
                .one_time_keyboard(oneTimeKeyboard)
                .build();
    }

    /**
     * Create Keyboard in one column
     *
     * @param buttons buttons
     * @return keyboard
     */
    public static ReplyKeyboardMarkup createOneColumnReplyKeyBoardMarkup(boolean oneTimeKeyboard, KeyboardButton... buttons) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(Arrays.stream(buttons).map(x -> List.of(x)).collect(Collectors.toList()))
                .one_time_keyboard(oneTimeKeyboard)
                .build();
    }
}
