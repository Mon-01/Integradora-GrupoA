package com.example.aplicacioncorporativa.controller;

import grupo.a.modulocomun.DTO.UsuarioDTO;

import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import grupo.a.modulocomun.Entidades.Usuario;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class controladorRESTCorporativo {

    private final UsuarioService usuarioService;
    private final HttpSession session;



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
     @PostMapping("/registro")
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
    @GetMapping("/contador-total")
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping("/api/usuarios/cambiar-clave")
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
}


