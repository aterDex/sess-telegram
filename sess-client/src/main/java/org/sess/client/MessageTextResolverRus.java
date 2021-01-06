package org.sess.client;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageTextResolverRus implements MessageTextResolver {

    private static Map<String, String> dictionary = Map.of("in_progress", "Функционал в процессе разработки."
            ,"hello_and_register", "Привет %1$s! Я бот sess. И я помогу найти тебе компанию для пробежки. Но в начале давай зарегистрируем тебя. Скажи как нам к тебе обращаться в дальнейшем?"
            , "hello_and_register_get_email", "А сейчас напиши свой Email, чтобы стать ближе)"
            ,"hello_and_register_get_sex", "Скажи какого ты пола?"
            ,"hello_and_register_get_birthday", "Дата рождения? (Мы некому не скажем)"
            , "hello_and_register_get_city", "Город в котором ты хочешь бегать?"
            , "hello_and_register_check", "Почти готово! Проверь данные и так:\r\n" +
                    "ник: %1$s\r\n" +
                    "email: %2$s\r\n" +
                    "город: %3$s\r\n" +
                    "пол: %4$s\r\n" +
                    "день рождения: %5$s\r\n" +
                    "Все верно?"
    );

    @Override
    public String resolveTextById(String locale, String textId) {
        return dictionary.get(textId);
    }

    @Override
    public String resolveTextById(String locale, String textId, Object... param) {
        return String.format(resolveTextById(locale, textId), param);
    }
}
