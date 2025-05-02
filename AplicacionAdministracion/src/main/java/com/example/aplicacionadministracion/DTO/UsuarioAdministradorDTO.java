package com.example.aplicacionadministracion.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAdministradorDTO {
    private String email;
    private String clave;
}