package com.inpe.focosservice.entities;

import lombok.Data;

import java.util.List;

@Data
public class ResponseEstados {
    List<EstadosRow> dados;
}
