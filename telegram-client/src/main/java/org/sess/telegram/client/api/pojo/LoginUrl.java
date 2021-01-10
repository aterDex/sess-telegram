package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class LoginUrl {

    private String url;

    @JsonInclude(NON_NULL)
    private String forward_text;
    @JsonInclude(NON_NULL)
    private String bot_username;
    @JsonInclude(NON_NULL)
    private Boolean request_write_access;
}
