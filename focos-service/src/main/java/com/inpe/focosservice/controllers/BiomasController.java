package com.inpe.focosservice.controllers;

import com.inpe.focosservice.entities.*;
import com.inpe.focosservice.services.BiomasService;
import com.inpe.focosservice.services.PaisesService;
import com.inpe.focosservice.utils.CsvUtils;
import com.inpe.focosservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value = "/biomas")
public class BiomasController {
    public final BiomasService biomasService;

    @Autowired
    public BiomasController(BiomasService biomasService) {
        this.biomasService = biomasService;

    }

    @GetMapping("")
    public ResponseEntity<?> getBiomas(
            @RequestParam(name = "data_inicio", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_inicio,
            @RequestParam(name = "data_fim", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_fim,
            @RequestParam(name = "satelite", required = false, defaultValue = "referencia") String satelite,
            @RequestParam(name = "csv", required = false, defaultValue = "false") Boolean csv,
            @RequestParam(name = "id_pais", required = false) String bioma,
            @RequestParam(name = "por_dia", required = false, defaultValue = "true") Boolean por_dia
    ) {
        // Caso algum dos valores seja nulo adiciona o valor padrao
        LocalDate[] datas_padroes = DateUtils.standardDateProcedure(data_inicio, data_fim);

        data_inicio = datas_padroes[0];
        data_fim = datas_padroes[1];

        if (!DateUtils.dataIsValid(data_inicio, data_fim)) {
            // Trata caso as datas nao passem pelo processo de validacao
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datas invalidas, formato padrao yyyyMMdd.");
        }

        if(por_dia) {
            List<BiomasRowPorDia> db_response = this.biomasService.getBiomasPorDia(
                    data_inicio,
                    data_fim,
                    satelite,
                    bioma
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseBiomasPorDia response = new ResponseBiomasPorDia();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        } else {
            List<BiomasRow> db_response = this.biomasService.getBiomas(
                    data_inicio,
                    data_fim,
                    satelite,
                    bioma
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseBiomas response = new ResponseBiomas();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
    }
}
