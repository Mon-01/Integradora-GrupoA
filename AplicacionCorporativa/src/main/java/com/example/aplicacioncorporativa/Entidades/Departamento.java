package com.example.aplicacioncorporativa.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_departamento")
    private UUID id_dept;

    private String nombre_dept;
    private String cod;
    private String loc;
    private BigDecimal presupuesto;
}
