package com.example.aplicacioncorporativa.DTO.Auxiliares;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Data
@Embeddable
public class Periodo {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
