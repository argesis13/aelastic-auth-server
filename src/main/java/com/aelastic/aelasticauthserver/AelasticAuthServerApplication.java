package com.aelastic.aelasticauthserver;

import netscape.javascript.JSObject;
import org.codehaus.jackson.JsonNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableResourceServer
@RestController
public class AelasticAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AelasticAuthServerApplication.class, args);
    }

    @GetMapping("/user")
    public ResponseEntity<?> bla() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "bla");
        return ResponseEntity.ok().body(map);
    }

}

