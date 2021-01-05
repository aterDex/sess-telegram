package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class Animation {

    private String file_id;
    private String file_unique_id;
    private int width;
    private int height;
    private int duration;
    private PhotoSize thumb;
    private String file_name;
    private String mime_type;
    private String file_size;
}
