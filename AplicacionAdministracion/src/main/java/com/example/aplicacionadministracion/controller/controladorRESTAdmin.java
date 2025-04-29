package com.example.aplicacionadministracion.controller;


import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class controladorRESTAdmin {

    private final UsuarioAdministradorService service;

    @Autowired
    public controladorRESTAdmin(UsuarioAdministradorService service) {
        this.service = service;
    }

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody UsuarioAdministradorDTO dto,
                                        HttpSession session) {
        if (service.validarCredenciales(dto)) {
            session.setAttribute("adminLogueado", dto);
            return ResponseEntity.ok("Autenticación correcta");
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerDetalleEmpleado(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                //si el metodo encuentra un empleado lo pasa a json
                .map(ResponseEntity::ok)
                //en caso de no encontrar al empleado devuelve 404
                .orElse(ResponseEntity.notFound().build());
    }

}
