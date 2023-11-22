package com.inpe.focosservice.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstadosRowPorDia {
    String estado;
    Long focos;
    LocalDate data;
}
