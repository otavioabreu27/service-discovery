package com.inpe.auxiliaresservice.controllers;

import com.inpe.auxiliaresservice.services.MunicipiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/municipios")
public class MunicipiosController {
    public final MunicipiosService municipiosService;

    @Autowired
    public MunicipiosController(MunicipiosService municipiosService) {
        this.municipiosService = municipiosService;
    }

    @GetMapping("")
    public ResponseEntity<String> getMunicipios(
            @RequestParam(name = "id_pais", required = false) Integer id_pais,
            @RequestParam(name = "id_estado", required = false) Integer id_estado,
            @RequestParam(name = "nome_pais", required = false) String nome_pais,
            @RequestParam(name = "nome_estado", required = false) String nome_estado,
            @RequestParam(name = "nome_municipio", required = false) String nome_municipio
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            return new ResponseEntity<>(
                    this.municipiosService.getMunicipios(
                            id_pais,
                            id_estado,
                            nome_pais,
                            nome_estado,
                            nome_municipio
                    ),
                    headers,
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }
}
