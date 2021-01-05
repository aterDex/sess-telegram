package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class User {

    private int id;
    private boolean is_bot;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private Boolean can_join_groups;
    private Boolean can_read_all_group_messages;
    private Boolean supports_inline_queries;
}
