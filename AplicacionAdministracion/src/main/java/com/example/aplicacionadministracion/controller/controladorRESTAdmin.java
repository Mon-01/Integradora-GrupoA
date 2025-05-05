package com.example.aplicacionadministracion.controller;


import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import grupo.a.modulocomun.Servicios.NominaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class controladorRESTAdmin {

    private final UsuarioAdministradorService service;

    @Autowired private NominaService nominaService;


    @Autowired
    public controladorRESTAdmin(UsuarioAdministradorService service) {
        this.service = service;
    }

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/loginAdmin") //error 400
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

    @PostMapping("/nomina")
    public ResponseEntity<?> crearNomina(@RequestBody NominaDTO nominaDTO) {
        try {
            // Validación básica
            if (nominaDTO.getEmpleado() == null || nominaDTO.getEmpleado().getId_empleado() == null) {
                return ResponseEntity.badRequest().body("El empleado es requerido");
            }

            if (nominaDTO.getLineas() == null || nominaDTO.getLineas().isEmpty()) {
                return ResponseEntity.badRequest().body("Debe haber al menos una línea en la nómina");
            }

            NominaDTO nuevaNomina = nominaService.crearNomina(nominaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNomina);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la nómina: " + e.getMessage());
        }
    }
}
