package com.inpe.focosservice.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BiomasRowPorDia {
    String bioma;
    Long focos;
    LocalDate data;
}
