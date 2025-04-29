package com.example.aplicacionadministracion.controller;

import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.Entidades.Empleado;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String mostrarInicioAdmin(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "") String departamento,
            @RequestParam(required = false, defaultValue = "0") Long salario,
            Model model, HttpSession session) {

        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");
        if (dto == null) return "redirect:/admin/login";

        model.addAttribute("adminEmail", dto.getEmail());

        // guardar los filtros en el modelo para que el HTML los conserve
        model.addAttribute("nombre", nombre);
        model.addAttribute("departamento", departamento);
        model.addAttribute("salario", salario);

        List<Empleado> empleados = adminService.buscarPorParametros(nombre, departamento, salario);
        model.addAttribute("empleados", empleados);

        return "inicio-admin";
    }



}




