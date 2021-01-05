package org.sess.telegram.client.api.pojo;

import lombok.Data;

@Data
public class PhotoSize {

    private String file_id;
    private String file_unique_id;
    private int width;
    private int height;
    private Integer file_size;
}
