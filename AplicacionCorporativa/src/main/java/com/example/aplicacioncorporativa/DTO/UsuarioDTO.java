package com.example.aplicacioncorporativa.DTO;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private UUID id_usuario;
    private String email;
    private String clave;
    private String confirmarClave;
    private EmpleadoDTO empleado;

}

