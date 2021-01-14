package org.sess.client.impl;

import org.sess.client.pojo.TelegramUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${sess.clients.user.name}", url = "${sess.clients.user.url}")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/users/telegram")
    void createUser(TelegramUser user);
}
