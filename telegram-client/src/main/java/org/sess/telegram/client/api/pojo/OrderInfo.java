package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class OrderInfo {

    private String name;
    private String phone_number;
    private String email;
    private ShippingAddress shipping_address;
}
