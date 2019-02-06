package com.aelastic.aelasticauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableResourceServer
public class AelasticAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AelasticAuthServerApplication.class, args);
    }

    @GetMapping("/user")
    public String getUsers() {
        return "bla";
    }


}

