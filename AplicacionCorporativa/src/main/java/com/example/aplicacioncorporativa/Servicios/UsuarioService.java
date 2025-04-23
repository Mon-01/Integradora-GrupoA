package com.example.aplicacioncorporativa.Servicios;

import com.example.aplicacioncorporativa.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Usuario;
import com.example.aplicacioncorporativa.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
                    usuario.setClave(dto.getClave());
                    usuario.setConfirmarClave(dto.getConfirmarClave());
                    return usuario;
                })
                .map(usuarioRepository::save); // guarda y devuelve el Optional
    }


}
