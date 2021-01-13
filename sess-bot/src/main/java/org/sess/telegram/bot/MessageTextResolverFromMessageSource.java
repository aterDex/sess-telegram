package org.sess.telegram.bot;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageTextResolverFromMessageSource implements MessageTextResolver {

    private final MessageSource messageSource;

    public MessageTextResolverFromMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String resolveTextById(String localeTag, MessageTextKey textId) {
        return resolveInner(localeTag, textId, null);
    }

    @Override
    public String resolveTextById(String localeTag, MessageTextKey textId, Object... param) {
        return resolveInner(localeTag, textId, param);
    }

    private String resolveInner(String localeTag, MessageTextKey textId, Object[] param) {
        var locale = Locale.ENGLISH;
        if (localeTag != null && !localeTag.isEmpty()) {
            locale = Locale.forLanguageTag(localeTag);
        }
        return messageSource.getMessage(textId.toString(), param, locale);
    }
}
