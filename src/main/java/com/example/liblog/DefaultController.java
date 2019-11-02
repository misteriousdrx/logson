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

        Logson.error("Mensagem erro");
        Logson.warning("Mensagem warning");
        Logson.info("Mensagem info");
        Logson.debug("Mensagem debug");
        Logson.trace("Mensagem trace");


        try {

            float a = 10 / 0;

        } catch (ArithmeticException e) {
            Logson.error("Divisão por zero", e);

        }

        return ResponseEntity.ok("it works!");

    }

}
