package com.example.aplicacionadministracion.controller;

// Importación de clases necesarias para el controlador.
import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import grupo.a.modulocomun.Servicios.NominaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller // Indica que esta clase es un controlador de Spring MVC que gestionará peticiones HTTP.
public class controladorMVCAdmin {

    // Logger para registrar mensajes en la consola o fichero (útil para depuración y monitoreo).
    private static final Logger logger = LoggerFactory.getLogger(controladorMVCAdmin.class);

    // Inyección automática del servicio de nómina.
    @Autowired private NominaService nominaService;

    // Inyección automática del repositorio de nómina (posiblemente para operaciones CRUD directas).
    @Autowired private NominaRepository nominaRepository;

    // Servicio para acceder a datos y lógica de empleados.
    @Autowired private EmpleadoService empleadoService;

    // Metodo GET que muestra el formulario de login de administrador.
    @GetMapping("/admin/login")
    public String mostrarLoginAdmin() {
        return "login-admin"; // Retorna la vista "login-admin.html" ubicada en /templates/.
    }

    // Inyección del servicio que maneja lógica de administradores (ej: autenticación, filtrado).
    @Autowired
    private UsuarioAdministradorService adminService;

    // Muestra la pantalla de inicio del panel administrador, con filtros para buscar empleados.
    @GetMapping("/admin/inicio")
    public String mostrarInicioAdmin(
            @RequestParam(required = false) String nombre, // Filtro opcional por nombre.
            @RequestParam(required = false) String departamento, // Filtro opcional por departamento.
            @RequestParam(required = false, defaultValue = "0") Long salario, // Filtro opcional por salario mínimo.
            Model model, HttpSession session) { // Model para pasar datos a la vista, HttpSession para controlar sesión.

        // Recupera del session el DTO del administrador logueado.
        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");

        // Si no hay sesión iniciada, redirige al login.
        if (dto == null) return "redirect:/admin/login";

        // Llama al servicio para obtener empleados según los filtros proporcionados.
        List<Empleado> empleados = adminService.buscarFiltrados(nombre, departamento, salario);

        // Logs de depuración: muestra cantidad de empleados encontrados y sus datos.
        logger.info("Número de empleados encontrados: {}", empleados.size());
        empleados.forEach(e -> logger.info("Empleado: {} {}, Depto: {}, Salario: {}",
                e.getNombre(), e.getApellido(),
                e.getDepartamento() != null ? e.getDepartamento().getNombre_dept() : "N/A",
                e.getSalarioAnual()));

        // Añade atributos al modelo que se usarán en la vista HTML.
        model.addAttribute("nombre", nombre != null ? nombre : ""); // Para mantener el filtro seleccionado.
        model.addAttribute("departamento", departamento != null ? departamento : "");
        model.addAttribute("salario", salario);
        model.addAttribute("adminEmail", dto.getEmail()); // Mostrar el email del admin logueado.
        model.addAttribute("empleados", empleados); // Lista de empleados filtrados.

        return "inicio-admin"; // Retorna la vista HTML principal del administrador.
    }

    // Muestra el detalle de un empleado a través de su ID.
    @GetMapping("/admin/detalle/{id}")
    public String mostrarDetalleEmpleado(@PathVariable Long id, Model model) {
        model.addAttribute("empleadoId", id); // Pasa el ID del empleado a la vista.
        return "/detalles/DetalleEmpleado"; // Renderiza la vista "DetalleEmpleado.html".
    }

    // Muestra una lista de todas las nóminas disponibles.
    @GetMapping("/listado")
    public String listar(Model model, Map map) {

        model.addAttribute("nominas", nominaService.obtenerTodasNominas()); // ← Aquí pasas los DTO, no las entidades
        return "listadoNominas"; // Muestra la vista con la lista de nóminas.
    }

    // Muestra un formulario para crear una nueva nómina.
    @GetMapping("/nueva")
    public String nueva(Model model) {
        NominaDTO dto = new NominaDTO(); // Crea un nuevo objeto de tipo NominaDTO vacío.
        dto.setFecha(LocalDate.now()); // Asigna la fecha actual como predeterminada.
        model.addAttribute("nomina", dto); // Pasa el objeto nómina a la vista.
        model.addAttribute("empleados", empleadoService.obtenerTodosEmpleados()); // Lista de empleados para seleccionar uno.
        return "nuevaNomina"; // Renderiza la vista "nuevaNomina.html".
    }
    @GetMapping("/productos")
    public String product(){
        return "producto";
    }

    @GetMapping("/listadoProductos")
    public String listadoP(){
        return "listadoProductos";
    }

    // Muestra el detalle de una nómina específica para un empleado determinado.
    @GetMapping("/admin/nomina/{empleadoId}")
    public String mostrarDetalleNomina(@PathVariable Long empleadoId, Model model) {
        model.addAttribute("empleadoId", empleadoId); // Pasa el ID del empleado a la vista.
        return "/detalles/detalle-nomina"; // Retorna la vista "detalle-nomina.html".
    }

    //botón para cerrar la sesión
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //simplemente invalidamos la sesión
        request.getSession().invalidate();

        //obtenemos las cookies
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            Arrays.stream(cookies)
                    // Filtra las cookies de sesión
                    .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                    .forEach(cookie -> {

                cookie.setMaxAge(0);  // Establece la edad de la cookie a 0, para eliminarla
                cookie.setPath("/");  // Asegura que el path sea el adecuado (esto es importante para que se borre correctamente)
                response.addCookie(cookie);
            });
        }
        //y redirigimos al login
        return "redirect:/admin/login";
    }
}


