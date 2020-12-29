package org.sess.telegram.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.sess.telegram.client.api.TelegramAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/telegram/v1.0")
public class TelegramAdapterController {

    private final TelegramAdapter telegramAdapter;

    public TelegramAdapterController(TelegramAdapter telegramAdapter) {
        this.telegramAdapter = telegramAdapter;
    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {
        if (telegramAdapter.check()) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
    }
}
