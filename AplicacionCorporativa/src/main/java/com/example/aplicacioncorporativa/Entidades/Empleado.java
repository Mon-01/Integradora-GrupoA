package com.example.aplicacioncorporativa.Entidades;

import com.example.aplicacioncorporativa.Entidades.Auxiliares.Periodo;
import com.example.aplicacioncorporativa.Entidades.Auxiliares.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Empleado extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_empleado")
    private UUID id_empleado;

    private boolean administrador;

    @Embedded
    private Periodo periodo;

    private String motivo_cese;

    @OneToOne
    @JoinColumn(name = "id_usuario",foreignKey = @ForeignKey(name = "FK_empleado_usuario_id_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_empleado_departamento_id_dept",nullable = true)
    private Departamento departamento;
}
