package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class SuccessfulPayment {

    private String currency;
    private int total_amount;
    private String invoice_payload;
    private String shipping_option_id;
    private String telegram_payment_charge_id;
    private String provider_payment_charge_id;
}
