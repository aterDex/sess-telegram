package org.sess.telegram.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BotServerConfig {

    @Bean
    public RestTemplate botServer() {
        return new RestTemplate();
    }
}
