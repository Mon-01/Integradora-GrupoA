package com.example.aplicacioncorporativa.Entidades.Auxiliares;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Data
@Embeddable
public class Periodo {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
