package org.sess.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableFeignClients
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"org.sess.telegram.bot", "org.sess.telegram.client", "org.sess.client"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
