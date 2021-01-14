package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class PreCheckoutQuery {

    private String id;
    private User from;
    private String currency;
    private int total_amount;
    private String invoice_payload;
    private String shipping_option_id;
    private OrderInfo order_info;
}
