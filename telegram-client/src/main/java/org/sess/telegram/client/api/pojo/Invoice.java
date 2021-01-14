package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class Invoice {

    private String title;
    private String description;
    private String start_parameter;
    private String currency;
    private int total_amount;
}
