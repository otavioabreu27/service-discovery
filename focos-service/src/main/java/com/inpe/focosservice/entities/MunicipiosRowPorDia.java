package com.inpe.focosservice.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MunicipiosRowPorDia {
    String municipio;
    String estado;
    Long focos;
    LocalDate data;
}
