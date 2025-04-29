package com.example.aplicacioncorporativa.Servicios;

import com.example.aplicacioncorporativa.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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
                    return usuario;
                })
                .map(usuarioRepository::save); // guarda y devuelve el Optional
    }
    public boolean comprobarPassword(Usuario usuario, String claveSinEncriptar) {
        return passwordEncoder.matches(claveSinEncriptar, usuario.getClave());
    }


}
