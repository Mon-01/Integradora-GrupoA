package com.example.aplicacionadministracion.component;


import com.example.aplicacionadministracion.Repositorios.UsuarioAdministradorRepository;
import grupo.a.modulocomun.Entidades.UsuarioAdministrador;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminMetadatos {

    private final UsuarioAdministradorRepository repo;
    private final PasswordEncoder encoder;

    @PostConstruct
    public void insertarAdministradores() {
        crearAdmin("admin1@empresa.com", "admin123");
        crearAdmin("admin2@empresa.com", "admin123");
        crearAdmin("admin3@empresa.com", "admin123");
    }

    private void crearAdmin(String email, String password) {
        if (repo.findByEmail(email).isEmpty()) {
            UsuarioAdministrador admin = new UsuarioAdministrador();
            admin.setEmail(email);
            admin.setClave(encoder.encode(password));
            repo.save(admin);
        }
    }
}
