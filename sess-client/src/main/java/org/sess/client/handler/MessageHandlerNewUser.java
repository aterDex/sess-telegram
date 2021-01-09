package org.sess.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.sess.client.MessageTextResolver;
import org.sess.client.SessTemplate;
import org.sess.client.pojo.City;
import org.sess.client.pojo.Sex;
import org.sess.client.pojo.TelegramUser;
import org.sess.telegram.client.api.pojo.KeyboardButton;
import org.sess.telegram.client.api.pojo.Message;

import java.time.LocalDateTime;

@Slf4j
public class MessageHandlerNewUser implements MessageHandler {

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;
    private Steps currentStep = Steps.FIRST;
    private final TelegramUser.TelegramUserBuilder userBuilder = TelegramUser.builder();

    public MessageHandlerNewUser(SessTemplate sessTemplate, MessageTextResolver messageTextResolver) {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
    }

    @Override
    public void handler(Message msg, MessageHandlerContext context) {
        switch (currentStep) {
            case FIRST:
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register", msg.getFrom().getFirst_name()
                                ))
                );
                currentStep = Steps.NAME;
                break;
            case NAME:
                userBuilder.nickname(msg.getText());
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_get_email"
                                ))
                );
                currentStep = Steps.EMAIL;
                break;
            case EMAIL:
                userBuilder.email(msg.getText());
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_get_sex"
                                ), TelegramMessageUtils.createKeyBoard(
                                        true,
                                        KeyboardButton.builder()
                                                .text(messageTextResolver.resolveTextById(
                                                        msg.getFrom().getLanguage_code(),
                                                        "sex_men"
                                                ))
                                                .build(),
                                        KeyboardButton.builder()
                                                .text(messageTextResolver.resolveTextById(
                                                        msg.getFrom().getLanguage_code(),
                                                        "sex_women"
                                                ))
                                                .build()))
                );
                currentStep = Steps.SEX;
                break;
            case SEX:
                userBuilder.sex(Sex.MALE);
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_get_birthday"
                                ))
                );
                currentStep = Steps.BIRTHDAY;
                break;
            case BIRTHDAY:
                userBuilder.birthday(LocalDateTime.now());
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_get_city"
                                ))
                );
                currentStep = Steps.CITY;
                break;
            case CITY:
                userBuilder.city(new City(0, msg.getText()));
                TelegramUser user = userBuilder.build();
                context.getTelegramTemplate().sendMessage(
                        TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_check",
                                        user.getNickname(),
                                        user.getEmail(),
                                        user.getCity().getAddress(),
                                        user.getSex().toString(),
                                        user.getBirthday().toString()
                                ),
                                TelegramMessageUtils.createKeyBoard(
                                        true,
                                        KeyboardButton.builder()
                                                .text(messageTextResolver.resolveTextById(
                                                        msg.getFrom().getLanguage_code(),
                                                        "yes"
                                                ))
                                                .build(),
                                        KeyboardButton.builder()
                                                .text(messageTextResolver.resolveTextById(
                                                        msg.getFrom().getLanguage_code(),
                                                        "no"
                                                ))
                                                .build()
                                ))
                );
                currentStep = Steps.CHECK;
                break;
            case CHECK:
                String okAns = messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "yes"
                );
                if (okAns.equalsIgnoreCase(msg.getText())) {
                    try {
                        userBuilder.telegramId(msg.getChat().getId());
                        sessTemplate.createUser(userBuilder.build());
                        context.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_ok"
                                )));
                    } catch (Exception e) {
                        log.error("", e);
                        context.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                                messageTextResolver.resolveTextById(
                                        msg.getFrom().getLanguage_code(),
                                        "hello_and_register_error"
                                )));
                    }
                } else {
                    context.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                            messageTextResolver.resolveTextById(
                                    msg.getFrom().getLanguage_code(),
                                    "hello_and_register_abort"
                            )));
                }
                context.getMessageHandlerStore().removeLastHandler(msg.getChat().getId());
                break;
        }
    }

    private enum Steps {
        FIRST, NAME, EMAIL, SEX, BIRTHDAY, CITY, CHECK
    }
}
