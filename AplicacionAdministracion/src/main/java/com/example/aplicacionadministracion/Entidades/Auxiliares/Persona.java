package com.example.aplicacionadministracion.Entidades.Auxiliares;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Data
/*
 * dos opciones para que usuario y empleado hereden de persona
 * @Inheritance(strategy = InheritanceType.JOINED)
 * @MappedSuperclass
 * */
public class Persona {
    private String nombre;
    private String apellido;
    private String genero;
    private String telefono;
    private String email;
    private String direccion;
    private int cod_postal;
    private LocalDate fecha_nacimiento;
}