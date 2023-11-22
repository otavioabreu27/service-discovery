package com.inpe.focosservice.controllers;

import com.inpe.focosservice.entities.PaisesRow;
import com.inpe.focosservice.entities.PaisesRowPorDia;
import com.inpe.focosservice.entities.ResponsePaises;
import com.inpe.focosservice.entities.ResponsePaisesPorDia;
import com.inpe.focosservice.utils.CsvUtils;
import com.inpe.focosservice.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inpe.focosservice.services.PaisesService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value = "/paises")
public class PaisesController {
    public final PaisesService paisesService;

    @Autowired
    public PaisesController(PaisesService paisesService) {
        this.paisesService = paisesService;

    }

    @GetMapping("")
    public ResponseEntity<?> getPaises(
            @RequestParam(name = "data_inicio", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_inicio,
            @RequestParam(name = "data_fim", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_fim,
            @RequestParam(name = "satelite", required = false, defaultValue = "referencia") String satelite,
            @RequestParam(name = "csv", required = false, defaultValue = "false") Boolean csv,
            @RequestParam(name = "id_pais", required = false) Long id_pais,
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
            List<PaisesRowPorDia> db_response = this.paisesService.getPaisesPorDia(
                    data_inicio,
                    data_fim,
                    satelite,
                    id_pais
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponsePaisesPorDia response = new ResponsePaisesPorDia();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        } else {
            List<PaisesRow> db_response = this.paisesService.getPaises(
                    data_inicio,
                    data_fim,
                    satelite,
                    id_pais
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponsePaises response = new ResponsePaises();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
    }
}
