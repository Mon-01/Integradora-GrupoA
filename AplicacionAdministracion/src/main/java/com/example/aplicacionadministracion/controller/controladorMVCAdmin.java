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
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false, defaultValue = "0") Long salario,
            Model model, HttpSession session) {

        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");
        if (dto == null) return "redirect:/admin/login";

        List<Empleado> empleados = adminService.buscarFiltrados(nombre, departamento, salario);

        // Log de diagnóstico
        logger.info("Número de empleados encontrados: {}", empleados.size());
        empleados.forEach(e -> logger.info("Empleado: {} {}, Depto: {}, Salario: {}",
                e.getNombre(), e.getApellido(),
                e.getDepartamento() != null ? e.getDepartamento().getNombre_dept() : "N/A",
                e.getSalarioAnual()));

        model.addAttribute("nombre", nombre != null ? nombre : "");
        model.addAttribute("departamento", departamento != null ? departamento : "");
        model.addAttribute("salario", salario);
        model.addAttribute("adminEmail", dto.getEmail());
        model.addAttribute("empleados", empleados);

        return "inicio-admin";
    }

    @GetMapping("/admin/detalle/{id}")
    public String mostrarDetalleEmpleado(@PathVariable Long id, Model model) {
        model.addAttribute("empleadoId", id);
        return "detalle";
    }

}




