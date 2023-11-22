package com.inpe.focosservice.controllers;

import com.inpe.focosservice.entities.*;
import com.inpe.focosservice.services.EstadosService;
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
@RequestMapping("/estados")
public class EstadosController {
    private final EstadosService estadosService;

    @Autowired
    public EstadosController(EstadosService estadosService) {
        this.estadosService = estadosService;
    }

    @GetMapping("")
    public ResponseEntity<?> getEstados(
            @RequestParam(name = "data_inicio", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_inicio,
            @RequestParam(name = "data_fim", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate data_fim,
            @RequestParam(name = "satelite", required = false, defaultValue = "referencia") String satelite,
            @RequestParam(name = "csv", required = false, defaultValue = "false") Boolean csv,
            @RequestParam(name = "id_pais", required = true, defaultValue = "33") Long id_pais,
            @RequestParam(name = "id_estado", required = false) Long id_estado,
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
            List<EstadosRowPorDia> db_response = this.estadosService.getEstadosPorDia(
                    data_inicio,
                    data_fim,
                    satelite,
                    id_pais,
                    id_estado
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseEstadosPorDia response = new ResponseEstadosPorDia();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        } else {
            List<EstadosRow> db_response = this.estadosService.getEstados(
                    data_inicio,
                    data_fim,
                    satelite,
                    id_pais,
                    id_estado
            );
            if(csv) {
                String response = CsvUtils.convertListToCSV(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseEstados response = new ResponseEstados();
                response.setDados(db_response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
    }
}
