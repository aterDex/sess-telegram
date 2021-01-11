package org.sess.telegram.bot;

import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.sess.telegram.bot.MessageTextKey.*;

@Component
public class MessageTextResolverRus implements MessageTextResolver {

    private static final Map<MessageTextKey, String> dictionary;

    static {
        dictionary = ofEntries(entry(IN_PROGRESS, "Функционал в процессе разработки.")
                , entry(HELLO_AND_REGISTER, "Привет %1$s! Я бот sess. И я помогу найти тебе компанию для пробежки. Но в начале давай зарегистрируем тебя.")
                , entry(HELLO_AND_REGISTER_GEN_NAME, "Скажи как нам к тебе обращаться в дальнейшем?")
                , entry(HELLO_AND_REGISTER_GET_EMAIL, "А сейчас напиши свой Email, чтобы стать ближе)")
                , entry(HELLO_AND_REGISTER_GET_EMAIL_AGAIN, "Email не верный, попробуй ввести его еще раз")
                , entry(HELLO_AND_REGISTER_GET_SEX, "Скажи какого ты пола?")
                , entry(HELLO_AND_REGISTER_GET_BIRTHDAY, "Год рождения?")
                , entry(HELLO_AND_REGISTER_GET_CITY, "Город в котором ты хочешь бегать?")
                , entry(HELLO_AND_REGISTER_GET_CITY_BUTTON, "Отправить гео код")
                , entry(HELLO_AND_REGISTER_CHECK, "Почти готово! Проверь данные и так:\r\nник: %1$s\r\nemail: %2$s\r\nгород: %3$s\r\nпол: %4$s\r\nдень рождения: %5$s\r\nВсе верно?")
                , entry(SEX_MEN, "М")
                , entry(SEX_WOMEN, "Ж")
                , entry(YES, "Да")
                , entry(NO, "Нет")
                , entry(AGAIN, "Заново")
                , entry(HELLO_AND_REGISTER_OK, "Вы зарегистрированы")
                , entry(HELLO_AND_REGISTER_ERROR, "Ошибка при регистрации")
                , entry(HELLO_AND_REGISTER_ABORT, "Регистрация отменена")
        );
    }

    @Override
    public String resolveTextById(String locale, MessageTextKey textId) {
        return dictionary.getOrDefault(textId, "");
    }

    @Override
    public String resolveTextById(String locale, MessageTextKey textId, Object... param) {
        return String.format(resolveTextById(locale, textId), param);
    }
}
