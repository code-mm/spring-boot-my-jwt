package com.my.app.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {
    @GetMapping(value = "/test")
    public String test() {
        return "{\"test\":\"true\"}";
    }
}