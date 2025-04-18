package com.example.aplicacioncorporativa.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoDTO {
    private UUID id_dept;
    private String nombre_dept;
    private String loc;
}

