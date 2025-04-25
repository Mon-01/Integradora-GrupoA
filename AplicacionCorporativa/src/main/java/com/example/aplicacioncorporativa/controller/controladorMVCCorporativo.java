package com.example.aplicacioncorporativa.controller;

import com.example.aplicacioncorporativa.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Usuario;
import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class controladorMVCCorporativo {

    private final UsuarioService usuarioService;
    private final HttpSession session;

    public controladorMVCCorporativo(UsuarioService usuarioService, HttpSession session) {
        this.usuarioService = usuarioService;
        this.session = session;
    }
    @GetMapping("/reg")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "corporativo/registro.html";
    }
    @GetMapping("/login")
    public String mostrarFormularioEmail(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "corporativo/inicio";
    }

    @PostMapping("/email")
    public String procesarEmail(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(Optional.of(usuarioDTO));
        if (usuario.isEmpty()) {
            model.addAttribute("error", "El usuario introducido no existe.");
            return "corporativo/inicio.html";
        }

        session.setAttribute("emailTemporal", usuarioDTO.getEmail());
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "corporativo/contrasenia.html";
    }

    @PostMapping("/password")
    public String procesarPassword(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, Model model) {
        String email = (String) session.getAttribute("emailTemporal");
        usuarioDTO.setEmail(email);

        Optional<Usuario> usuario = usuarioService.buscarPorEmail(Optional.of(usuarioDTO));
        if (usuario.isEmpty() || !usuarioService.comprobarPassword(usuario.get(), usuarioDTO.getClave())) {
            model.addAttribute("error", "Contraseña incorrecta.");
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "corporativo/contrasenia.html";
        }

        session.setAttribute("usuarioLogueado", usuario.get());
        return "redirect:/inicio";
    }
}

