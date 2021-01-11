package org.sess.telegram.bot;

import org.springframework.stereotype.Component;

import java.util.Map;
import static java.util.Map.*;


@Component
public class MessageTextResolverRus implements MessageTextResolver {

    static {
        dictionary = ofEntries(entry("in_progress", "Функционал в процессе разработки.")
                , entry("hello_and_register", "Привет %1$s! Я бот sess. И я помогу найти тебе компанию для пробежки. Но в начале давай зарегистрируем тебя.")
                , entry("hello_and_register_gen_name", "Скажи как нам к тебе обращаться в дальнейшем?")
                , entry("hello_and_register_get_email", "А сейчас напиши свой Email, чтобы стать ближе)")
                , entry("hello_and_register_get_sex", "Скажи какого ты пола?")
                , entry("hello_and_register_get_birthday", "Дата рождения? (Мы некому не скажем)")
                , entry("hello_and_register_get_city", "Город в котором ты хочешь бегать?")
                , entry("hello_and_register_get_city_button", "Отправить гео код")
                , entry("hello_and_register_check", "Почти готово! Проверь данные и так:\r\nник: %1$s\r\nemail: %2$s\r\nгород: %3$s\r\nпол: %4$s\r\nдень рождения: %5$s\r\nВсе верно?")
                , entry("sex_men", "М")
                , entry("sex_women", "Ж")
                , entry("yes", "Да")
                , entry("no", "Нет")
                , entry("again", "Заново")
                , entry("hello_and_register_ok", "Вы зарегистрированы")
                , entry("hello_and_register_error", "Ошибка при регистрации")
                , entry("hello_and_register_abort", "Регистрация отменена")
        );
    }

    private static final Map<String, String> dictionary;

    @Override
    public String resolveTextById(String locale, String textId) {
        return dictionary.getOrDefault(textId, "");
    }

    @Override
    public String resolveTextById(String locale, String textId, Object... param) {
        return String.format(resolveTextById(locale, textId), param);
    }
}
