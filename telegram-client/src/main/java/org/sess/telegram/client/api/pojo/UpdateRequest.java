package org.sess.telegram.client.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRequest {

    private Integer timeout;
    private Integer offset;
}
