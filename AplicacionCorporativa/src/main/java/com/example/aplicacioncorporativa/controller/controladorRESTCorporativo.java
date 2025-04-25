package com.example.aplicacioncorporativa.controller;

import com.example.aplicacioncorporativa.DTO.UsuarioDTO;

import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import grupo.a.modulocomun.Entidades.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class controladorRESTCorporativo {

    private final UsuarioService usuarioService;

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
    }


