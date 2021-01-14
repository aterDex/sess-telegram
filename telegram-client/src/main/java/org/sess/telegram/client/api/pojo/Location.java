package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class Location {

    private float longitude;
    private float latitude;
    private float horizontal_accuracy;
    private int live_period;
    private int heading;
    private int proximity_alert_radius;
}
