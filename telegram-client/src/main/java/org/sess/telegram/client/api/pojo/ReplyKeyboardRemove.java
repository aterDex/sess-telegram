package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyKeyboardRemove {

    private boolean remove_keyboard;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean selective;
}
