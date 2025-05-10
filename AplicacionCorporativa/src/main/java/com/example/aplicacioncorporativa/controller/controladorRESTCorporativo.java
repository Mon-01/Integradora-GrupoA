package com.example.aplicacioncorporativa.controller;

import ch.qos.logback.core.model.Model;
import grupo.a.modulocomun.DTO.UsuarioDTO;

import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import grupo.a.modulocomun.DTO.filtros.filtrosNominasDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Nomina;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Servicios.NominaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class controladorRESTCorporativo {

    private final UsuarioService usuarioService;
    private final HttpSession session;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NominaService nominaService;

     /*   @PostMapping("/registro") // cambiamos la ruta para diferenciarlo
        public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
            if (usuarioService.buscarPorEmail(Optional.of(usuarioDTO)).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe una cuenta con ese email.");
            }

            Optional<Usuario> guardado = usuarioService.registrarUsuarioDesdeDTO(Optional.of(usuarioDTO));

            return guardado
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("No se pudo guardar el usuario."));
        }

      */
     @PostMapping("/registro")  //error 405
     public String registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
         if (usuarioService.buscarPorEmail(Optional.of(usuarioDTO)).isPresent()) {
             return "Ya existe una cuenta con ese email.";
         }

         Optional<Usuario> guardado = usuarioService.registrarUsuarioDesdeDTO(Optional.of(usuarioDTO));

         if (guardado.isPresent()) {
             return "Usuario registrado exitosamente.";
         } else {
             return "Error al registrar el usuario.";
         }
     }

    @GetMapping("/contador-total")  //error 405
    public Integer obtenerContadorTotal() {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return 0;
        }

        Map<String, Integer> contadorGlobal = (Map<String, Integer>) session.getServletContext().getAttribute("contadorGlobal");
        if (contadorGlobal == null) {
            return 0;
        }

        return contadorGlobal.getOrDefault(usuarioLogueado.getEmail(), 0);
    }

    @PutMapping("/api/usuarios/cambiar-clave")  //error 405
    public ResponseEntity<String> cambiarClave(@RequestBody UsuarioDTO dto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dto.getId_usuario());

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Validación de la respuesta secreta
            if (!usuario.getRespuestaSecreta().equalsIgnoreCase(dto.getRespuestaSecreta().trim())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Respuesta secreta incorrecta");
            }

            String claveEncriptada = passwordEncoder.encode(dto.getClave());
            usuario.setClave(claveEncriptada);
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/usuario/actualizar")
    public ResponseEntity<?> actualizarPerfil(@RequestBody Map<String, String> datosPerfil, HttpSession session) {
        // Obtener usuario de la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay usuario logueado");
        }

        try {
            // Actualizar datos básicos del usuario
            if (datosPerfil.containsKey("email")) {
                usuarioLogueado.setEmail(datosPerfil.get("email"));
            }

            // Actualizar datos del empleado si existe
            if (usuarioLogueado.getEmpleado() != null) {
                Empleado empleado = usuarioLogueado.getEmpleado();

                if (datosPerfil.containsKey("nombre")) {
                    empleado.setNombre(datosPerfil.get("nombre"));
                }
                if (datosPerfil.containsKey("apellido")) {
                    empleado.setApellido(datosPerfil.get("apellido"));
                }
                if (datosPerfil.containsKey("telefono")) {
                    empleado.setTelefono(datosPerfil.get("telefono"));
                }

                empleadoRepository.save(empleado);
            }

            // Guardar cambios del usuario
            usuarioRepository.save(usuarioLogueado);

            // Actualizar usuario en sesión
            session.setAttribute("usuarioLogueado", usuarioLogueado);

            return ResponseEntity.ok(Map.of(
                    "message", "Perfil actualizado correctamente",
                    "status", "success"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Error al actualizar: " + e.getMessage(),
                            "status", "error"
                    ));
        }
    }

    @PostMapping("/filtroNominasEmpleado")
    public ResponseEntity<?> obtenerFiltroNominas(@RequestBody filtrosNominasDTO filtros) {
        List<Nomina> nominas = nominaService.filtrarPorNomina(filtros.getNombre(), filtros.getDepartamento(), filtros.getFecha());

        List<filtrosNominasDTO> nominaDTOs = nominas.stream()
                .map(n -> nominaService.returnConsultaFiltradoNominas(n))
                .collect(Collectors.toList());


        return nominas != null ? ResponseEntity.ok(nominaDTOs) : ResponseEntity.status(404).body("No hay resultados");
    }

}
