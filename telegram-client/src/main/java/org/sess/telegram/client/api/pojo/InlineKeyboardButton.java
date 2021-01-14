package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonDeserialize(builder = InlineKeyboardButton.InlineKeyboardButtonBuilder.class)
@Data
@Builder
public class InlineKeyboardButton {

    private String text;

    @JsonInclude(NON_NULL)
    private String url;
    @JsonInclude(NON_NULL)
    private LoginUrl login_url;
    @JsonInclude(NON_NULL)
    private String callback_data;
    @JsonInclude(NON_NULL)
    private String switch_inline_query;
    @JsonInclude(NON_NULL)
    private String switch_inline_query_current_chat;
    @JsonInclude(NON_NULL)
    private Boolean pay;
}
