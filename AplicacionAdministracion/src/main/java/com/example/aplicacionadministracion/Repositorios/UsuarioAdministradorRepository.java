package com.example.aplicacionadministracion.Repositorios;

import grupo.a.modulocomun.Entidades.UsuarioAdministrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioAdministradorRepository extends JpaRepository<UsuarioAdministrador, UUID> {
    Optional<UsuarioAdministrador> findByEmail(String email);
}