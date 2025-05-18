package com.example.aplicacionadministracion.controller;


import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.DTO.Auxiliares.CatalogoProductosDTO;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.DTO.filtros.*;
import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.LineaNomina;
import grupo.a.modulocomun.Entidades.Nomina;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Servicios.DepartamentoService;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import grupo.a.modulocomun.Servicios.ImportacionCatalogoService;
import grupo.a.modulocomun.Servicios.NominaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/admin")
public class controladorRESTAdmin {

    private final UsuarioAdministradorService service;

    @Autowired private ImportacionCatalogoService importacionCatalogoService;
    @Autowired private NominaService nominaService;
    @Autowired private NominaRepository nominaRepository;
    @Autowired private DepartamentoService departamentoService;

    @Autowired
    public controladorRESTAdmin(UsuarioAdministradorService service) {
        this.service = service;
    }

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@RequestBody UsuarioAdministradorDTO dto, HttpSession session) {
        if (service.validarCredenciales(dto)) {
         //   session.setAttribute("adminLogueado",dto);
            // Devuelve un token simple o indicador de éxito
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
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
    public ResponseEntity<?> crearNomina(@Validated @RequestBody NominaDTO nominaDTO, BindingResult result) {
        try {
            // Validación básica
            if (nominaDTO.getEmpleado() == null || nominaDTO.getEmpleado().getId_empleado() == null) {
                return ResponseEntity.badRequest().body("El empleado es requerido");
            }

            if (nominaDTO.getLineas() == null || nominaDTO.getLineas().isEmpty()) {
                return ResponseEntity.badRequest().body("Debe haber al menos una línea en la nómina");
            }

            if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errores.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errores);
            }

            NominaDTO nuevaNomina = nominaService.crearNomina(nominaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNomina);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear la nómina: " + e.getMessage());
        }
    }

    @GetMapping("/nomina/{id}")
    public ResponseEntity<List<NominaDTO>> obtenerDetalleNomina(@PathVariable Long id) {
        List<NominaDTO> nominas = nominaService.obtenerNominasPorEmpleado(id);
        return ResponseEntity.ok(nominas);
    }

    // Añade este método al servicio NominaService

    @DeleteMapping("/del/nomina/{id}")
    public ResponseEntity<?> eliminarNomina(@PathVariable Long id) {
        try {
            nominaService.eliminarNomina(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la nómina: " + e.getMessage());
        }
    }

    @PostMapping("/filtroNominas")
    public ResponseEntity<?> obtenerFiltroNominas(@RequestBody filtrosNominasDTO filtros) {
        List<Nomina> nominas = nominaService.filtrarPorNomina(filtros.getNombre(), filtros.getDepartamento(), filtros.getFecha());

        List<filtrosNominasDTO> nominaDTOs = nominas.stream()
                .map(n -> nominaService.returnConsultaFiltradoNominas(n))
                .collect(Collectors.toList());


        return nominas != null ? ResponseEntity.ok(nominaDTOs) : ResponseEntity.status(404).body("No hay resultados");
    }
    @PostMapping("/importar")
    public ResponseEntity<String> importar(@RequestBody CatalogoProductosDTO catalogo) {
        try {
            String resultado = importacionCatalogoService.importarCatalogo(catalogo);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al importar: " + e.getMessage());
        }
    }

    @PostMapping("/buscar")
    public List<ProductoResultadoDTO> buscar(@RequestBody ProductoBusquedaDTO filtros) {
        return importacionCatalogoService.buscarProductos(filtros);
    }

    // Nuevo endpoint para servir datos a Apache
    @GetMapping("/empleados")
    public List<EmpleadoDTO> obtenerTodosEmpleadosParaApache() {
        return empleadoService.obtenerTodosEmpleados().stream()
                .map(this::convertirEmpleadoADTO)
                .collect(Collectors.toList());
    }

}
