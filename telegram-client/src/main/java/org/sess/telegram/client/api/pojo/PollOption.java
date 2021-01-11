package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class PollOption {

    private String text;
    private int voter_count;
}
