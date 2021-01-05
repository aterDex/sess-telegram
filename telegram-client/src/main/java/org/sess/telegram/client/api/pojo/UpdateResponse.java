package org.sess.telegram.client.api.pojo;

import lombok.Data;

import java.util.List;

@Data
public class UpdateResponse {

    private boolean ok;
    private List<Update> result;
}
