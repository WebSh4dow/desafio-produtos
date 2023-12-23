package com.gerenciamento.produtos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestApiController {

    @GetMapping("/test")
    public ResponseEntity<?> getPing() {
        return ResponseEntity.ok().build();
    }
}
