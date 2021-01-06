package org.sess.client.handler;

import org.sess.client.MessageTextResolver;
import org.sess.client.SessTemplate;
import org.sess.client.pojo.City;
import org.sess.client.pojo.Sex;
import org.sess.client.pojo.TelegramUser;
import org.sess.telegram.client.api.pojo.Message;

import java.time.LocalDateTime;

import static org.sess.client.handler.MessageHandlerUtils.sendText;

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
                sendText(msg, context.getTelegramTemplate(),
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                "hello_and_register", msg.getFrom().getFirst_name()
                        )
                );
                currentStep = Steps.NAME;
                break;
            case NAME:
                userBuilder.nickname(msg.getText());
                sendText(msg, context.getTelegramTemplate(), messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "hello_and_register_get_email"
                ));
                currentStep = Steps.EMAIL;
                break;
            case EMAIL:
                userBuilder.email(msg.getText());
                sendText(msg, context.getTelegramTemplate(), messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "hello_and_register_get_sex"
                ));
                currentStep = Steps.SEX;
                break;
            case SEX:
                userBuilder.sex(Sex.MALE);
                sendText(msg, context.getTelegramTemplate(), messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "hello_and_register_get_birthday"
                ));
                currentStep = Steps.BIRTHDAY;
                break;
            case BIRTHDAY:
                userBuilder.birthday(LocalDateTime.now());
                sendText(msg, context.getTelegramTemplate(), messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "hello_and_register_get_city"
                ));
                currentStep = Steps.CITY;
                break;
            case CITY:
                userBuilder.city(new City(0, msg.getText()));
                TelegramUser user = userBuilder.build();
                sendText(msg, context.getTelegramTemplate(), messageTextResolver.resolveTextById(
                        msg.getFrom().getLanguage_code(),
                        "hello_and_register_check",
                        user.getNickname(),
                        user.getEmail(),
                        user.getCity().getAddress(),
                        user.getSex().toString(),
                        user.getBirthday().toString()));
                context.getMessageHandlerStore().removeLastHandler(msg.getChat().getId());
                break;
        }
    }

    private enum Steps {
        FIRST, NAME, EMAIL, SEX, BIRTHDAY, CITY, AGAIN
    }

}
