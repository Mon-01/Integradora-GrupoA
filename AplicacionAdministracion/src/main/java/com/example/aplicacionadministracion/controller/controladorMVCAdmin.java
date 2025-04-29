package com.example.aplicacionadministracion.controller;

import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;

@Controller
public class controladorMVCAdmin {
    private static final Logger logger = LoggerFactory.getLogger(controladorMVCAdmin.class);




        @GetMapping("/admin/login")
        public String mostrarLoginAdmin() {
            UsuarioAdministradorDTO usu = new UsuarioAdministradorDTO();
            return "login-admin"; // buscará templates/admin-login.html
        }



    @Autowired
    private UsuarioAdministradorService adminService; // o el servicio que accede a los empleados

    @GetMapping("/admin/inicio")
    public String mostrarInicioAdmin(Model model, HttpSession session) {
        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");

        if (dto == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("adminEmail", dto.getEmail());
        model.addAttribute("empleados", adminService.obtenerTodosLosEmpleados()); // esto debe retornar List<Empleado>

        return "inicio-admin"; // Thymeleaf renderizará esta vista
    }


}




