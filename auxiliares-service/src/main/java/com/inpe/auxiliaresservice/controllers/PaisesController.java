package com.inpe.auxiliaresservice.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.inpe.auxiliaresservice.services.PaisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/paises")
public class PaisesController {
    public final PaisesService paisesService;

    @Autowired
    public PaisesController(PaisesService paisesService) {
        this.paisesService = paisesService;
    }

    @GetMapping("")
    public ResponseEntity<String> getPaises(
            @RequestParam(name = "nome_pais", required = false) String nome_pais
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            return new ResponseEntity<>(
                    this.paisesService.getPaises(nome_pais),
                    headers,
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}
