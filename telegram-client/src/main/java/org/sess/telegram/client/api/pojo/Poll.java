package org.sess.telegram.client.api.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Poll {

    private String id;
    private String question;
    private List<PollOption> options;
    private int total_voter_count;
    private boolean is_closed;
    private boolean is_anonymous;
    private String type;
    private boolean allows_multiple_answers;
    private Integer correct_option_id;
    private String explanation;
    private List<MessageEntity> explanation_entities;
    private Integer open_period;
    private Integer close_date;
}
