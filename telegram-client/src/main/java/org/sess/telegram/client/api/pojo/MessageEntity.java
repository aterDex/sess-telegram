package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class MessageEntity {

    private String type;
    private int offset;
    private int length;
    private String url;
    private User user;
    private String language;
}
