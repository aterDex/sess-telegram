package org.sess.telegram.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.sess.client.api.GeoResolver;
import org.sess.client.api.SessTemplate;
import org.sess.client.pojo.Sex;
import org.sess.client.pojo.TelegramUser;
import org.sess.telegram.bot.MessageTextKey;
import org.sess.telegram.bot.MessageTextResolver;
import org.sess.telegram.client.api.handler.MessageHandlerContext;
import org.sess.telegram.client.api.handler.UpdateHandler;
import org.sess.telegram.client.api.pojo.InlineKeyboardButton;
import org.sess.telegram.client.api.pojo.KeyboardButton;
import org.sess.telegram.client.api.pojo.Message;
import org.sess.telegram.client.api.pojo.Update;
import org.sess.telegram.client.impl.TelegramMessageUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier("updateHandlerNewUser")
public class UpdateHandlerNewUser implements UpdateHandler {

    public static final String KEYBOARD_INFO = "KeyboardInfo";
    public static final String MSG = "update";
    public static final String MHC = "mhc";

    private final SessTemplate sessTemplate;
    private final MessageTextResolver messageTextResolver;
    private final TelegramUser.TelegramUserBuilder userBuilder = TelegramUser.builder();
    private final GeoResolver geoResolver;
    private final StateMachine<Steps, Event> stateMachine;

    public UpdateHandlerNewUser(SessTemplate sessTemplate, MessageTextResolver messageTextResolver, GeoResolver geoResolver) throws Exception {
        this.sessTemplate = sessTemplate;
        this.messageTextResolver = messageTextResolver;
        this.geoResolver = geoResolver;
        this.stateMachine = buildMachine();
        this.stateMachine.start();
    }

    @Override
    public boolean handler(Update update, MessageHandlerContext context) {
        if (update.getMessage() != null) {
            try {
                stateMachine.sendEvent(MessageBuilder
                        .withPayload(Event.DATA)
                        .setHeader(MSG, update.getMessage())
                        .setHeader(MHC, context)
                        .build());
            } catch (Exception e) {
                log.error("", e);
            }
            return true;
        }
        return false;
    }

    private StateMachine<Steps, Event> buildMachine() throws Exception {
        StateMachineBuilder.Builder<Steps, Event> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(Steps.FIRST)
                .end(Steps.CLOSE)
                .state(Steps.HELLO, this::hello)

                .state(Steps.REQ_NAME, this::requestName)
                .state(Steps.RES_NAME, this::responseName)

                .state(Steps.REQ_EMAIL, this::requestEmail)
                .state(Steps.RES_EMAIL, this::responseEmail)

                .state(Steps.REQ_SEX, this::requestSex)
                .state(Steps.RES_SEX, this::responseSex)

                .state(Steps.REQ_BIRTHDAY, this::requestBirthday)
                .state(Steps.RES_BIRTHDAY, this::responseBirthday)

                .state(Steps.REQ_CITY, this::requestCity)
                .state(Steps.RES_CITY, this::responseCity)

                .state(Steps.REQ_CHECK, this::requestCheck)
                .state(Steps.RES_CHECK, this::responseCheck)

                .state(Steps.CLOSE, this::close)
                .states(EnumSet.allOf(Steps.class));

        builder.configureTransitions()
                .withExternal().source(Steps.FIRST).target(Steps.HELLO).event(Event.DATA).and()

                .withExternal().source(Steps.HELLO).target(Steps.REQ_NAME).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_NAME).target(Steps.RES_NAME).event(Event.DATA).and()

                .withExternal().source(Steps.RES_NAME).target(Steps.REQ_EMAIL).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_EMAIL).target(Steps.RES_EMAIL).event(Event.DATA).guard(this::checkEmail).and()

                .withExternal().source(Steps.RES_EMAIL).target(Steps.REQ_SEX).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_SEX).target(Steps.RES_SEX).event(Event.DATA).and()

                .withExternal().source(Steps.RES_SEX).target(Steps.REQ_BIRTHDAY).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_BIRTHDAY).target(Steps.RES_BIRTHDAY).event(Event.DATA).and()

                .withExternal().source(Steps.RES_BIRTHDAY).target(Steps.REQ_CITY).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_CITY).target(Steps.RES_CITY).event(Event.DATA).and()

                .withExternal().source(Steps.RES_CITY).target(Steps.REQ_CHECK).event(Event.NEXT).and()
                .withExternal().source(Steps.REQ_CHECK).target(Steps.RES_CHECK).event(Event.DATA).and()

                .withExternal().source(Steps.RES_CHECK).target(Steps.REQ_NAME).event(Event.AGAIN).and()
                .withExternal().source(Steps.RES_CHECK).target(Steps.CLOSE).event(Event.NEXT).and()
        ;
        return builder.build();
    }

    private boolean checkEmail(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        var check = EmailValidator.getInstance().isValid(msg.getText());
        if (!check) {
            msgContext.getTelegramTemplate().sendMessage(
                    TelegramMessageUtils.createAnswer(msg,
                            messageTextResolver.resolveTextById(
                                    msg.getFrom().getLanguage_code(),
                                    MessageTextKey.HELLO_AND_REGISTER_GET_EMAIL_AGAIN))
            );
        }
        return check;
    }

    private void next(Event event, StateContext<Steps, Event> stepsEventStateContext) {
        stepsEventStateContext.getStateMachine().sendEvent(
                MessageBuilder.createMessage(event, stepsEventStateContext.getMessageHeaders()));
    }

    private void hello(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                MessageTextKey.HELLO_AND_REGISTER, msg.getFrom().getFirst_name()
                        ))
        );
        next(Event.NEXT, stepsEventStateContext);
    }

    private void responseName(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        userBuilder.nickname(msg.getText());
        next(Event.NEXT, stepsEventStateContext);
    }

    private void responseEmail(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        userBuilder.email(msg.getText());
        next(Event.NEXT, stepsEventStateContext);
    }

    private void responseBirthday(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        userBuilder.birthday(Integer.parseInt(msg.getText()));
        next(Event.NEXT, stepsEventStateContext);
    }

    private void responseCity(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        userBuilder.city(
                geoResolver.resolveCity(msg.getFrom().getLanguage_code()
                        , msg.getLocation().getLatitude()
                        , msg.getLocation().getLongitude())
        );
        next(Event.NEXT, stepsEventStateContext);
    }

    private void responseCheck(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        var languageCode = msg.getFrom().getLanguage_code();
        var keyboardInfo = (Map<String, Integer>) stepsEventStateContext
                .getStateMachine().getExtendedState()
                .getVariables().get(KEYBOARD_INFO);

        switch (keyboardInfo.getOrDefault(msg.getText(), 0)) {
            case 0 -> {
                msgContext.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                languageCode,
                                MessageTextKey.HELLO_AND_REGISTER_ABORT
                        )));
                next(Event.NEXT, stepsEventStateContext);
            }
            case 1 -> {
                try {
                    userBuilder.telegramId(msg.getChat().getId());
                    sessTemplate.createUser(userBuilder.build());
                    msgContext.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                            messageTextResolver.resolveTextById(
                                    languageCode,
                                    MessageTextKey.HELLO_AND_REGISTER_OK
                            )));
                } catch (Exception e) {
                    log.error("", e);
                    msgContext.getTelegramTemplate().sendMessage(TelegramMessageUtils.createAnswer(msg,
                            messageTextResolver.resolveTextById(
                                    languageCode,
                                    MessageTextKey.HELLO_AND_REGISTER_ERROR
                            )));
                }
                next(Event.NEXT, stepsEventStateContext);
            }
            case 2 -> next(Event.AGAIN, stepsEventStateContext);
        }
    }

    private void close(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        msgContext.getUpdateHandlerStore().removeLastHandler(msg.getChat().getId());
    }

    private void responseSex(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var keyboardInfo = (Map<String, Sex>) stepsEventStateContext
                .getStateMachine().getExtendedState()
                .getVariables().get(KEYBOARD_INFO);
        userBuilder.sex(keyboardInfo.get(msg.getText()));
        next(Event.NEXT, stepsEventStateContext);
    }

    private void requestSex(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        var languageCode = msg.getFrom().getLanguage_code();
        var men = messageTextResolver.resolveTextById(languageCode, MessageTextKey.SEX_MEN);
        var women = messageTextResolver.resolveTextById(languageCode, MessageTextKey.SEX_WOMEN);
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                languageCode,
                                MessageTextKey.HELLO_AND_REGISTER_GET_SEX
                        ), TelegramMessageUtils.createOneRowReplyKeyBoardMarkup(
                                true,
                                KeyboardButton.builder()
                                        .text(men)
                                        .build(),
                                KeyboardButton.builder()
                                        .text(women)
                                        .build()))
        );
        stepsEventStateContext.getStateMachine().getExtendedState().getVariables().put(KEYBOARD_INFO,
                Map.of(men, Sex.MALE, women, Sex.FEMALE));
    }

    private void requestEmail(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                MessageTextKey.HELLO_AND_REGISTER_GET_EMAIL)));
    }

    private void requestName(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                MessageTextKey.HELLO_AND_REGISTER_GEN_NAME, msg.getFrom().getFirst_name()
                        ))
        );
    }

    private void requestBirthday(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        InlineKeyboardButton bl = InlineKeyboardButton.builder().text("тест").callback_data("R").build();
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                MessageTextKey.HELLO_AND_REGISTER_GET_BIRTHDAY, msg.getFrom().getFirst_name()
                        ), TelegramMessageUtils.createOneColumnReplyKeyBoardMarkup(
                                true,
                                Stream.iterate(LocalDateTime.now().getYear() - 18, x -> x - 2).limit(82).map(x -> KeyboardButton.builder()
                                        .text(String.valueOf(x))
                                        .build()).toArray(KeyboardButton[]::new))
                ));
    }

    private void requestCity(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        String languageCode = msg.getFrom().getLanguage_code();
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                languageCode,
                                MessageTextKey.HELLO_AND_REGISTER_GET_CITY
                        ), TelegramMessageUtils.createOneRowReplyKeyBoardMarkup(true,
                                KeyboardButton.builder().text(messageTextResolver.resolveTextById(
                                        languageCode,
                                        MessageTextKey.HELLO_AND_REGISTER_GET_CITY_BUTTON
                                )).request_location(true).build())
                ));
    }

    private void requestCheck(StateContext<Steps, Event> stepsEventStateContext) {
        var msg = stepsEventStateContext.getMessageHeaders().get(MSG, Message.class);
        var msgContext = stepsEventStateContext.getMessageHeaders().get(MHC, MessageHandlerContext.class);
        var languageCode = msg.getFrom().getLanguage_code();
        var user = userBuilder.build();
        var yes = messageTextResolver.resolveTextById(languageCode, MessageTextKey.YES);
        var no = messageTextResolver.resolveTextById(languageCode, MessageTextKey.NO);
        var again = messageTextResolver.resolveTextById(languageCode, MessageTextKey.AGAIN);
        msgContext.getTelegramTemplate().sendMessage(
                TelegramMessageUtils.createAnswer(msg,
                        messageTextResolver.resolveTextById(
                                msg.getFrom().getLanguage_code(),
                                MessageTextKey.HELLO_AND_REGISTER_CHECK,
                                user.getNickname(),
                                user.getEmail(),
                                user.getCity().getAddress(),
                                user.getSex().toString(),
                                String.valueOf(user.getBirthday())
                        ),
                        TelegramMessageUtils.createOneRowReplyKeyBoardMarkup(
                                true,
                                KeyboardButton.builder()
                                        .text(yes)
                                        .build(),
                                KeyboardButton.builder()
                                        .text(no)
                                        .build(),
                                KeyboardButton.builder()
                                        .text(again)
                                        .build()
                        ))
        );
        stepsEventStateContext.getExtendedState().getVariables().put(KEYBOARD_INFO, Map.of(yes, 1, again, 2, no, 0));
    }

    private enum Steps {
        FIRST, HELLO,
        REQ_NAME, RES_NAME,
        REQ_EMAIL, RES_EMAIL,
        REQ_SEX, RES_SEX,
        REQ_BIRTHDAY, RES_BIRTHDAY,
        REQ_CITY, RES_CITY,
        REQ_CHECK, RES_CHECK, CLOSE
    }

    private enum Event {
        DATA, NEXT, AGAIN
    }
}
