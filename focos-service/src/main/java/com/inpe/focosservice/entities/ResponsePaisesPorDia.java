package com.inpe.focosservice.entities;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePaisesPorDia {
    List<PaisesRowPorDia> dados;
}
