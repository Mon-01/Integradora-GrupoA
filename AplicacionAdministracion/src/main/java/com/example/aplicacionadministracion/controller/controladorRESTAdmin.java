package com.example.aplicacionadministracion.controller;


import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import grupo.a.modulocomun.Servicios.NominaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class controladorRESTAdmin {

    private final UsuarioAdministradorService service;

    @Autowired private NominaService nominaService;
    @Autowired private NominaRepository nominaRepository;

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
    public ResponseEntity<?> obtenerDetalleEmpleado(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(empleado -> {
                    // Convertir empleado a DTO
                    EmpleadoDTO empleadoDTO = convertirEmpleadoADTO(empleado);

                    // Obtener nóminas del empleado
                    List<NominaDTO> nominasDTO = nominaService.obtenerNominasPorEmpleado(id);
                    empleadoDTO.setNominas(nominasDTO);

                    return ResponseEntity.ok(empleadoDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private EmpleadoDTO convertirEmpleadoADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId_empleado(empleado.getId_empleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getPrefijoTel() + " " + empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFecha_nacimiento());
        dto.setSalarioAnual(empleado.getSalarioAnual().toString());
        dto.setComisionAnual(empleado.getComisionAnual().toString());
        dto.setComentarios(empleado.getComentarios());

        // Datos del departamento
        if(empleado.getDepartamento() != null) {
            dto.setIdDepartamento(empleado.getDepartamento().getId_dept());
        }

        // Datos del usuario si existe
        if(empleado.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId_usuario(empleado.getUsuario().getId_usuario());
            usuarioDTO.setEmail(empleado.getUsuario().getEmail());
            usuarioDTO.setBloqueado(empleado.getUsuario().isBloqueado());
            usuarioDTO.setMotivoBloqueo(empleado.getUsuario().getMotivoBloqueo());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
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
