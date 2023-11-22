package com.inpe.focosservice.entities;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePaises {
    List<PaisesRow> dados;
}
