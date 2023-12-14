package com.inpe.auxiliaresservice.controllers;

import com.inpe.auxiliaresservice.services.EstadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/estados")
@RestController
public class EstadosController {
    private final EstadosService estadosService;

    @Autowired
    public EstadosController(EstadosService estadosService) {
        this.estadosService = estadosService;
    }

    @GetMapping("")
    public ResponseEntity<String> getEstados(
            @RequestParam(name = "id_pais", required = false) Integer id_pais,
            @RequestParam(name = "nome_pais", required = false) String nome_pais,
            @RequestParam(name = "nome_estado", required = false) String nome_estado
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            return new ResponseEntity<>(
                    this.estadosService.getEstados(
                            id_pais,
                            nome_pais,
                            nome_estado
                    ),
                    headers,
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}
