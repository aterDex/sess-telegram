package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyboardButton {

    private String text;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean request_contact;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean request_location;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private KeyboardButtonPollType request_poll;
}
