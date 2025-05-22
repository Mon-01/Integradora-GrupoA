package com.example.aplicacionadministracion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BloqueoUsuarioDTO {
    private UUID id;
    private String motivo;
    private LocalDateTime tiempo;
}
