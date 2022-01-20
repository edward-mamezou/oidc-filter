package com.mamezou_tech.example.controller.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamezou_tech.example.controller.model.Hello;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiImpl implements HelloApi {

    @Autowired
    private ObjectProvider<HttpServletRequest> requestFactory;

    private final String bearer = "bearer ";

    private final ObjectMapper mapper;

    public HelloApiImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public ResponseEntity<Hello> helloGet() {
        Hello res = new Hello();
        HttpServletRequest request = requestFactory.getIfAvailable();

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith(bearer)) {
            String token = authorization.substring(bearer.length());
            DecodedJWT decodedJWT = JWT.decode(token);
            try {
                Map<String, Object> payload = mapper.readValue(new String(Base64.getDecoder().decode(decodedJWT.getPayload()), StandardCharsets.UTF_8), new TypeReference<>() {});
                res.setMessage(mapper.writeValueAsString(payload));
            } catch (JsonProcessingException ex) {
                res.setMessage(ex.toString());
            }
        } else {
            res.setMessage("Hello World!");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
