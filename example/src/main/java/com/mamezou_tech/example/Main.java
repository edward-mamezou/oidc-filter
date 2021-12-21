package com.mamezou_tech.example;

import org.springframework.boot.SpringApplication;

public class Main {

    private static final String CLASS_NAME = "com.mamezou_tech.example.controller.api.OpenAPI2SpringBoot";

    public static void main(String[] args) throws Exception {
        Class appClass = Class.forName(CLASS_NAME);
        SpringApplication.run(appClass);
    }
}
