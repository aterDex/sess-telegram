package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class Contact {

    private String phone_number;
    private String first_name;
    private String last_name;
    private Integer user_id;
    private String vcard;
}
