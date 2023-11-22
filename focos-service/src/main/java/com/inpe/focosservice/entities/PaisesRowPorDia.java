package com.inpe.focosservice.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaisesRowPorDia {
    String pais;
    Long focos;
    LocalDate data;
}
