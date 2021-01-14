package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class InlineQuery {

    private String id;
    private User from;
    private Location location;
    private String query;
    private String offset;
}
