package com.example.aplicacioncorporativa.Entidades;

import com.example.aplicacioncorporativa.Entidades.Auxiliares.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_usuario")
    private UUID id_usuario;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String clave;

    @Transient // No se almacena, es momentáneo
    private String confirmarClave;

    @OneToOne(mappedBy = "usuario")
    //el usuario pertenece al empleado, la clave ajena va en empleado
    private Empleado empleado;
}
