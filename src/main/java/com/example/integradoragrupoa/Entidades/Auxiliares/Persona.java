package com.example.integradoragrupoa.Entidades.Auxiliares;

import jakarta.validation.constraints.NotBlank;
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
    private int edad;
    private LocalDate fecha_nacimiento;
    private int prefijoTel;
    private String telefono;
    private String email;
    private Direccion direccion;
    private int cod_postal;
    private String paisNacimiento;
    private String tipoDocumento;
    private String documento;

}
