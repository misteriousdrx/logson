package com.example.liblog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController {

    @GetMapping
    public ResponseEntity<?> get() {

        Logson.setCorrelationId(String.valueOf(new Date().getTime()));
//        Logson.info("Mensagem logada");
//        Logson.debug("DEBUG");
//        Logson.trace("TRACE");
        Logson.warning("WARN");
//        Logson.error("ERROR");

        return ResponseEntity.ok("it works!");

    }

}
