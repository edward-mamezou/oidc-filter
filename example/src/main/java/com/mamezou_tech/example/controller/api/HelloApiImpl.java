package com.mamezou_tech.example.controller.api;

import com.mamezou_tech.example.controller.model.Hello;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiImpl implements HelloApi {

    @Override
    public ResponseEntity<Hello> helloGet() {
        Hello res = new Hello();
        res.setMessage("Hello World");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
