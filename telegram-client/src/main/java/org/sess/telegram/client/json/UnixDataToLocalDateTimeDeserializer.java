package org.sess.telegram.client.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class UnixDataToLocalDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {

    public UnixDataToLocalDateTimeDeserializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(p.getIntValue()), ZoneOffset.UTC);
        }
        return (ZonedDateTime) ctxt.handleUnexpectedToken(_valueClass, p);
    }
}
