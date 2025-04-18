package com.example.aplicacioncorporativa.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private UUID id_usuario;
    private String email;
    private String clave;
    private String confirmarClave;
    private EmpleadoDTO empleado;
}