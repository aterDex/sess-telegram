package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class Audio {

    private String file_id;
    private String file_unique_id;
    private int duration;
    private String performer;
    private String title;
    private String file_name;
    private String mime_type;
    private Integer file_size;
    private PhotoSize thumb;
}
