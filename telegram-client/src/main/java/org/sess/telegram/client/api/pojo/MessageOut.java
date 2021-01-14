package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageOut {

    private String chat_id;
    private String text;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object reply_markup;
}
