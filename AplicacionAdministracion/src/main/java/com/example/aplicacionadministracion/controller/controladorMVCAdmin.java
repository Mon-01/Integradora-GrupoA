package com.example.aplicacionadministracion.controller;

// Importación de clases necesarias para el controlador.
import com.example.aplicacionadministracion.DTO.UsuarioAdministradorDTO;
import com.example.aplicacionadministracion.Servicios.UsuarioAdministradorService;
import grupo.a.modulocomun.DTO.LineaNominaDTO;
import grupo.a.modulocomun.DTO.NominaDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.LineaNomina;
import grupo.a.modulocomun.Entidades.Nomina;

import grupo.a.modulocomun.Repositorios.LineaNominaRepository;
import grupo.a.modulocomun.Repositorios.NominaRepository;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import grupo.a.modulocomun.Servicios.NominaService;
import grupo.a.modulocomun.Servicios.RepositoryManager;
import grupo.a.modulocomun.Servicios.ServiceManager;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
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
    @Autowired
    private RepositoryManager repositoryManager;
    @Autowired
    private ServiceManager serviceManager;
    @Autowired
    private LineaNominaRepository lineaNominaRepository;

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
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) List<String> departamentos,
            @RequestParam(required = false) BigDecimal salarioMin,
            @RequestParam(required = false) BigDecimal salarioMax,
            Model model, HttpSession session) {

        UsuarioAdministradorDTO dto = (UsuarioAdministradorDTO) session.getAttribute("adminLogueado");
        if (dto == null) return "redirect:/admin/login";
        List<Empleado> empleados = empleadoService.buscarFiltrados(nombre, departamentos, salarioMin, salarioMax);
        List<String> todosDepartamentos = empleadoService.obtenerNombresDepartamentos();

        model.addAttribute("nombre", nombre != null ? nombre : "");
        model.addAttribute("departamentosSeleccionados", departamentos != null ? departamentos : Collections.emptyList());
        model.addAttribute("todosDepartamentos", todosDepartamentos);
        model.addAttribute("salarioMin", salarioMin);
        model.addAttribute("salarioMax", salarioMax);
        model.addAttribute("adminEmail", dto.getEmail());
        model.addAttribute("empleados", empleados);

        return "inicio-admin";
    }
    // Método auxiliar para conversión segura
    private BigDecimal convertirStringABigDecimal(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(valor.replace(",", "."));  // Soporta tanto "." como "," como separador decimal
        } catch (NumberFormatException e) {
            return null;  // Si el formato es inválido, se ignora el filtro
        }
    }

    // Muestra el detalle de un empleado a través de su ID.
    @GetMapping("/admin/detalle/{id}")
    public String mostrarDetalleEmpleado(@PathVariable Long id, Model model) {
        model.addAttribute("empleadoId", id); // Pasa el ID del empleado a la vista.
        return "/DetalleEmpleado"; // Renderiza la vista "DetalleEmpleado.html".
    }

    // Muestra una lista de todas las nóminas disponibles.
    @GetMapping("/listado")
    public String listar(Model model, Map map) {

        model.addAttribute("nominas", nominaService.obtenerTodasNominas()); // ← Aquí pasas los DTO, no las entidades
        return "nominas/listadoNominas"; // Muestra la vista con la lista de nóminas.
    }
    @GetMapping("/nueva")
    public String nueva() {
        // Redirigir siempre a la vista en Apache
        return "redirect:http://vista1.nomina.com:80/";
    }
    /*
    // Muestra un formulario para crear una nueva nómina.
    @GetMapping("/nueva")
    public String nueva(Model model) {
        NominaDTO dto = new NominaDTO(); // Crea un nuevo objeto de tipo NominaDTO vacío.
        dto.setFecha(LocalDate.now()); // Asigna la fecha actual como predeterminada.
        model.addAttribute("nomina", dto); // Pasa el objeto nómina a la vista.
        model.addAttribute("empleados", empleadoService.obtenerTodosEmpleados()); // Lista de empleados para seleccionar uno.
        return "nuevaNomina";// Renderiza la vista "nuevaNomina.html".
      //  return "redirect:http://vista1.tudominio.com/";
    }

     */
    @GetMapping("/apacher2")
    public String apache2() {
        return "redirect:http://vista2.administrativa.com:80/";
    }



    // Muestra un formulario para crear una nueva nómina.


    @GetMapping("/apacher3")
    public String apache3(){
        return "redirect:http://vista3.corporativa.com:80/";

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
        return "/nominas/detalle-nomina"; // Retorna la vista "detalle-nomina.html".
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

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {

        Nomina nomina = nominaService.obtenerNomina(id);
        NominaDTO dto = nominaService.convertirADTO(nomina);
        model.addAttribute("nomina", dto);

        return "/nominas/editarNomina";
    }

    @PostMapping("/nomina/guardar")
    public String guardarNomina(@ModelAttribute NominaDTO nominaDTO) {
        Nomina nominaAnterior = nominaService.obtenerNomina(nominaDTO.getId());
        //eliminamos líneas antigüas excepto el salario base
        nominaAnterior.getLineas().removeIf(linea ->
                !"Salario Base".equalsIgnoreCase(linea.getDescripcion()));

        //eliminamos las líneas nulas que puedan dar conflictos
        nominaDTO.getLineas().removeIf(linea ->
                (linea.getConcepto() == null || linea.getConcepto().trim().isEmpty()) &&
                        linea.getPorcentaje() == null &&
                        linea.getImporteFijo() == null &&
                        linea.getCantidad() == null
        );

        nominaAnterior.getLineas().removeIf(linea ->
                !"Salario Base".equalsIgnoreCase(linea.getDescripcion()));

        List<LineaNomina> lineasActualizadas = nominaDTO.getLineas().stream().map(lineaDTO -> {
            //diferenciamos entre línea a actualizar y línea a insertar
            if (lineaDTO.getId() != null) {
                //al no ser null tiene id y la podemos actualizar
                Optional<LineaNomina> lineaExistenteOptional = nominaAnterior.getLineas().stream()
                        .filter(ln -> lineaDTO.getId().equals(ln.getId()))
                        .findFirst();

                if (lineaExistenteOptional.isPresent()) { // Añadida comprobación
                    LineaNomina lineaExistente = lineaExistenteOptional.get();
                    // Actualiza la línea existente
                    lineaExistente.setDescripcion(lineaDTO.getConcepto());
                    lineaExistente.setEsDevengo(lineaDTO.getEsDevengo());

                    //actualizamos el porcentaje o importe poniendo el contrario a cero
                    if (lineaDTO.getTipoValor() == 1) {
                        lineaExistente.setImporteFijo(BigDecimal.ZERO);
                        lineaExistente.setPorcentaje(lineaDTO.getPorcentaje() != null ? new BigDecimal(lineaDTO.getPorcentaje()) : BigDecimal.ZERO);
                    } else {
                        lineaExistente.setPorcentaje(BigDecimal.ZERO);
                        lineaExistente.setImporteFijo(lineaDTO.getImporteFijo() != null ? new BigDecimal(lineaDTO.getImporteFijo()) : BigDecimal.ZERO);
                    }
                    BigDecimal porcentaje = new BigDecimal(lineaDTO.getPorcentaje());
                    lineaExistente.setImporte(nominaService.calcularImportePorcentaje(nominaAnterior.getEmpleado().getSalarioAnual(), porcentaje));
                    return lineaExistente;
                } // else añadido para el caso de que no exista la línea con ese ID.
                else {
                    return null; // Devolvemos null para filtrar luego.
                }

            } else {
                LineaNomina lineaNueva = new LineaNomina();
                lineaNueva.setDescripcion(lineaDTO.getConcepto());
                lineaNueva.setEsDevengo(lineaDTO.getEsDevengo());

                if (lineaDTO.getTipoValor() == 1) {
                    lineaNueva.setImporteFijo(BigDecimal.ZERO);
                    lineaNueva.setPorcentaje(lineaDTO.getPorcentaje() != null ?  new BigDecimal(lineaDTO.getPorcentaje()) : BigDecimal.ZERO);
                } else {
                    lineaNueva.setPorcentaje(BigDecimal.ZERO);
                    lineaNueva.setImporteFijo(lineaDTO.getImporteFijo() != null ?  new BigDecimal(lineaDTO.getImporteFijo()) : BigDecimal.ZERO);
                }
                lineaNueva.setImporte(nominaService.calcularImportePorcentaje(nominaAnterior.getEmpleado().getSalarioAnual(), lineaNueva.getPorcentaje()));
                lineaNueva.setNomina(nominaAnterior);
                return lineaNueva;
            }

        }).filter(Objects::nonNull).collect(Collectors.toList());

//        nominaDTO.getLineas().stream().map(lineaDTO -> {
//            //diferenciamos entre líne a actualizar y línea a insertar
//            if (lineaDTO.getId() != null) {
//                //al no ser null tiene id y la podemos actualizar
//                Optional<LineaNomina> lineaExistenteOptional = nominaAnterior.getLineas().stream()
//                        .filter(ln -> lineaDTO.getId().equals(ln.getId()))
//                        .findFirst();
//
//                    LineaNomina lineaExistente = lineaExistenteOptional.get();
//                    // Actualiza la línea existente
//                    lineaExistente.setDescripcion(lineaDTO.getConcepto());
//                    lineaExistente.setEsDevengo(lineaDTO.getEsDevengo());
//
//                    //actualizamos el porcentaje o importe poniendo el contrario a cero
//                if(lineaExistenteOptional.get().getPorcentaje() != BigDecimal.ZERO) {
//                    lineaExistente.setImporteFijo(BigDecimal.ZERO);
//                    BigDecimal porcentaje = new BigDecimal(lineaDTO.getPorcentaje());
//                    lineaExistente.setPorcentaje(porcentaje);
//                    lineaExistente.setImporte(nominaService.calcularImportePorcentaje(nominaAnterior.getEmpleado().getSalarioAnual(),porcentaje));
//                }else{
//                    lineaExistente.setPorcentaje(BigDecimal.ZERO);
//                    BigDecimal importeFijo = new BigDecimal(lineaDTO.getImporteFijo());
//                    lineaExistente.setImporteFijo(importeFijo);
//                    lineaExistente.setImporte(importeFijo);
//                }
//                return lineaExistente;
//            } else {
//                LineaNomina lineaNueva = new LineaNomina();
//                lineaNueva.setId(lineaDTO.getId());
//                lineaNueva.setDescripcion(lineaDTO.getConcepto());
//                if(lineaExistenteOptional.get().getPorcentaje() != BigDecimal.ZERO) {
//                    lineaExistente.setImporteFijo(BigDecimal.ZERO);
//                    BigDecimal porcentaje = new BigDecimal(lineaDTO.getPorcentaje());
//                    lineaExistente.setPorcentaje(porcentaje);
//                    lineaExistente.setImporte(nominaService.calcularImportePorcentaje(nominaAnterior.getEmpleado().getSalarioAnual(),porcentaje));
//                }else{
//                    lineaExistente.setPorcentaje(BigDecimal.ZERO);
//                    BigDecimal importeFijo = new BigDecimal(lineaDTO.getImporteFijo());
//                    lineaExistente.setImporteFijo(importeFijo);
//                    lineaExistente.setImporte(importeFijo);
//                }
//            }
//
//                return lineaActualizada;
//
//
//        }).collect(Collectors.toList());

//                .forEach(lineaDTO -> {
//            LineaNomina linea = new LineaNomina();
//            BigDecimal importe;
//            // Mapeo manual seguro
//            linea.setDescripcion(lineaDTO.getConcepto());
//            linea.setEsDevengo(lineaDTO.getEsDevengo());
//
//            if (lineaDTO.getTipoValor() != null && lineaDTO.getTipoValor() == 1) {
//                // actualiza la línea a porcentaje y anula el importe fijo si lo hubiera
//                linea.setPorcentaje(lineaDTO.getPorcentaje() != null ?
//                        new BigDecimal(lineaDTO.getPorcentaje()) : BigDecimal.ZERO);
//                linea.setImporteFijo(BigDecimal.ZERO);
//            } else {
//                // actualiza la línea a importe fijo y anula el porcentaje si lo hubiera
//                linea.setImporteFijo(lineaDTO.getImporteFijo() != null ?
//                        new BigDecimal(lineaDTO.getImporteFijo()) : BigDecimal.ZERO);
//                linea.setPorcentaje(BigDecimal.ZERO);
//            }

            //dependiendo si la línea tiene porcentaje o importeFijo se calcula de una manera u otra


//            linea.setNomina(nominaAnterior);
//            nominaAnterior.getLineas().add(linea);
//        });

        nominaAnterior.setLineas(lineasActualizadas);
        repositoryManager.getNominaRepository().save(nominaAnterior);
        return "redirect:/listado";
//        repositoryManager.getNominaRepository().save(nominaAnterior);
//        return "redirect:/listado";
    }

}


