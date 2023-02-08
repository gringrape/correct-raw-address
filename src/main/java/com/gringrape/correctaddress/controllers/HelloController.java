package com.gringrape.correctaddress.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
final public class HelloController {
    @GetMapping
    public String greet() {
        return "Hello, World!";
    }
}
