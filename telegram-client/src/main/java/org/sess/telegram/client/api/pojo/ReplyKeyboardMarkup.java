package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReplyKeyboardMarkup {

    private List<List<KeyboardButton>> keyboard;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean resize_keyboard;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean one_time_keyboard;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean selective;
}
