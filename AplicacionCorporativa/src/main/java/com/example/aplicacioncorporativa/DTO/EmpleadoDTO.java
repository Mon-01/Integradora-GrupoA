package com.example.aplicacioncorporativa.DTO;
import com.example.aplicacioncorporativa.DTO.Auxiliares.Periodo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private UUID id_empleado;
    private boolean administrador;
    private Periodo periodo;
    private String motivo_cese;
    private UUID usuarioId;        // Referencia al Usuario
    private UUID departamentoId;   // Referencia al Departamento
}