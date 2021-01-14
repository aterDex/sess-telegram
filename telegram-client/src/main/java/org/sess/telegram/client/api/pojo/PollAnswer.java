package org.sess.telegram.client.api.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PollAnswer {

    private String poll_id;
    private User user;
    private List<Integer> option_ids;
}
