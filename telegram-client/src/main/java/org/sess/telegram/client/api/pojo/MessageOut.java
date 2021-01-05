package org.sess.telegram.client.api.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageOut {

    private String chat_id;
    private String text;
}
