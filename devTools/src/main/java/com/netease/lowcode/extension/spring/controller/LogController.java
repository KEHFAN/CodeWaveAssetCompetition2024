package com.netease.lowcode.extension.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @GetMapping("/rest/test")
    public String test() {
        return "hello";
    }

}
