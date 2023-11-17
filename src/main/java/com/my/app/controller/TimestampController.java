package com.my.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimestampController {

    @GetMapping(value = "/timestamp")
    public String timestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
