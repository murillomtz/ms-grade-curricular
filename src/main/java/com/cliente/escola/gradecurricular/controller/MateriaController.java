package com.cliente.escola.gradecurricular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/materia")
public class MateriaController {
    @GetMapping(value = "/")
    public ResponseEntity<String> helloWorldRest() {
        return ResponseEntity.status(HttpStatus.OK).body("Olá mundo rest");
    }


}
