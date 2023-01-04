package com.example.personalproject.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        String message = "강수빈";
        return message;
    }

}
