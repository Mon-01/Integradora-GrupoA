package com.example.aplicacioncorporativa.Servicios;

import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void bloquearUsuario(UUID id, String motivo, LocalDateTime hasta) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setBloqueado(true);
        usuario.setMotivoBloqueo(motivo);
        usuario.setFechaFinBloqueo(hasta);
        usuarioRepository.save(usuario);
    }

    public void desbloquearUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setBloqueado(false);
        usuario.setMotivoBloqueo(null);
        usuario.setFechaFinBloqueo(null);
        usuarioRepository.save(usuario);
    }

    public boolean isBloqueado(Usuario usuario) {
        if (!usuario.isBloqueado()) return false;

        if (usuario.getFechaFinBloqueo() != null &&
                //si la fecha es anterior a la actual
                usuario.getFechaFinBloqueo().isBefore(LocalDateTime.now())) {
            //desbloquea al usuario automáticamente
            desbloquearUsuario(usuario.getId_usuario());
            return false;
        }

        return true;
    }

    public Optional<Usuario> buscarPorEmail(Optional<UsuarioDTO> dtoOptional) {
        return dtoOptional
                .map(UsuarioDTO::getEmail) // extraes el email
                .flatMap(usuarioRepository::findByEmail); // buscas con ese email
    }

    public Optional<Usuario> registrarUsuarioDesdeDTO(Optional<UsuarioDTO> dtoOptional) {
        return dtoOptional
                .map(dto -> {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(dto.getEmail());
                    String claveEncriptada = passwordEncoder.encode(dto.getClave());
                    usuario.setClave(claveEncriptada);
                    usuario.setConfirmarClave(dto.getConfirmarClave());
                    usuario.setRespuestaSecreta(dto.getRespuestaSecreta());
                    return usuario;
                })
                .map(usuarioRepository::save); // guarda y devuelve el Optional
    }
    public boolean comprobarPassword(Usuario usuario, String claveSinEncriptar) {
        return passwordEncoder.matches(claveSinEncriptar, usuario.getClave());
    }


}
