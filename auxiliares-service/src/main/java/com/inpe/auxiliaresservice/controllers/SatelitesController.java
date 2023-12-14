package com.inpe.auxiliaresservice.controllers;

import com.inpe.auxiliaresservice.services.SatelitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/satelites")
@RestController
public class SatelitesController {
    private final SatelitesService satelitesService;

    @Autowired
    public SatelitesController(SatelitesService satelitesService) {
        this.satelitesService = satelitesService;
    }

    @GetMapping("")
    public ResponseEntity<?> getSatelites(
            @RequestParam(name = "nome_satelite", required = false) String nome_satelite
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            return new ResponseEntity<>(
                    this.satelitesService.getSatelites(nome_satelite),
                    headers,
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}
