package com.example.aplicacionadministracion.controller;


import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class controladorRESTAdmin {

    private final UsuarioAdministradorService service;

    @Autowired
    public controladorRESTAdmin(UsuarioAdministradorService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String loginAdministrador(@RequestBody UsuarioAdministradorDTO dto, HttpSession session) {
        Optional<?> resultado = service.autenticarAdministrador(dto);

        if (resultado.isEmpty()) {
            return "Credenciales incorrectas.";
        }

        // Guardamos el DTO en sesión (nunca la entidad)
        UsuarioAdministradorDTO dtoEnSesion = new UsuarioAdministradorDTO();
        dtoEnSesion.setEmail(dto.getEmail());
        session.setAttribute("adminLogueado", dtoEnSesion);

        return "OK";
    }
    @GetMapping("/admin/inicio")
    public String areaAdministrador(HttpSession session, Model model) {
        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");

        if (dto == null) {
            return "redirect:/login-admin";
        }

        model.addAttribute("adminEmail", dto.getEmail());
        return "corporativo/admin-inicio";
    }

}
