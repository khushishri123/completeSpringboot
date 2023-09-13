package com.example.springbootProfiles.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Value ( "${message}" )
    private String message;

    @RequestMapping("/")
    public String homepage()
    {
        return message;
    }

}
