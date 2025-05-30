package com.example.aplicacioncorporativa.controller;

import grupo.a.modulocomun.Validaciones.Resumen;
import grupo.a.modulocomun.Validaciones.paso1.Paso1;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Servicios.ServiceManager;
import grupo.a.modulocomun.Validaciones.paso2.Paso2;
import grupo.a.modulocomun.Validaciones.paso3.paso3;
import grupo.a.modulocomun.Validaciones.paso4.Paso4;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class controladorEmpleadoPasos {

    @Autowired
    private ServiceManager serviceManager;


    @GetMapping("/paso1")
    public String mostrarPaso1(HttpSession sesion, Model model) {
        EmpleadoDTO datosEmpleado = getEmpleadoFromSession(sesion);
        model.addAttribute("datos", datosEmpleado);
        model.addAttribute("generos", serviceManager.getGeneroService().obtenerTodos());
        model.addAttribute("paises", serviceManager.getPaisService().obtenerTodosPaises());
        return "empleadoPasos/datosPersonales";
    }

    @PostMapping("/paso1")
    public String procesarPaso1(HttpSession sesion, Model model,
                                @Validated(Paso1.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
                                BindingResult bindingResult) {

        //serviceManager.getEmpleadoService().validarMaestrosPaso1(datosEmpleado, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult);
            model.addAttribute("enviado", true);
            model.addAttribute("generos", serviceManager.getGeneroService().obtenerTodos());
            model.addAttribute("paises", serviceManager.getPaisService().obtenerTodosPaises());
            return "empleadoPasos/datosPersonales";
        } else {
            sesion.setAttribute("empleado", datosEmpleado);
            return "redirect:/paso2";
        }
    }

    @GetMapping("/paso2")
    public String mostrarPaso2(HttpSession sesion, Model model) {
        EmpleadoDTO datosEmpleado = getEmpleadoFromSession(sesion);
        model.addAttribute("tipoDoc", serviceManager.getTipoDocumentoService().obtenerTiposDocumento());
        model.addAttribute("vias", serviceManager.getTipoViaService().obtenerTipoVia());
        model.addAttribute("datos", datosEmpleado);
        return "empleadoPasos/datosContacto";
    }

    @PostMapping("/paso2")
    public String procesarPaso2(
            @Validated(Paso2.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
            BindingResult bindingResult,
            HttpSession sesion,
            Model model) {

//        serviceManager.getEmpleadoService().validarMaestrosPaso2(datosEmpleado, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("enviado", true);
            model.addAttribute("tipoDoc", serviceManager.getTipoDocumentoService().obtenerTiposDocumento());
            model.addAttribute("vias", serviceManager.getTipoViaService().obtenerTipoVia());
            return "empleadoPasos/datosContacto";
        } else {
            sesion.setAttribute("empleado", datosEmpleado);
            return "redirect:/paso3";
        }
    }

    @GetMapping("/paso3")
    public String mostrarPaso3(HttpSession sesion, Model model) {
        EmpleadoDTO datosEmpleado = getEmpleadoFromSession(sesion);
        model.addAttribute("datos", datosEmpleado);
        model.addAttribute("departamentos", serviceManager.getDepartamentoService().obtenerTodos());
        model.addAttribute("especialidades", serviceManager.getEspecialidadesService().obtenerEspecialidades());
        return "empleadoPasos/datosProfesionales";
    }

    @PostMapping("/paso3")
    public String procesarPaso3(HttpSession sesion, Model model,
                                @Validated(paso3.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
                                BindingResult bindingResult) {

//        serviceManager.getEmpleadoService().validarMaestrosPaso3(datosEmpleado, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("enviado", true);
            model.addAttribute("departamentos", serviceManager.getDepartamentoService().obtenerTodos());
            model.addAttribute("especialidades", serviceManager.getEspecialidadesService().obtenerEspecialidades());
            return "empleadoPasos/datosProfesionales";
        } else {
            sesion.setAttribute("empleado", datosEmpleado);
            return "redirect:/paso4";
        }
    }

    @GetMapping("/paso4")
    public String mostrarPaso4(HttpSession sesion, Model model) {
        EmpleadoDTO datosEmpleado = getEmpleadoFromSession(sesion);
        model.addAttribute("datos", datosEmpleado);
        model.addAttribute("entidades", serviceManager.getEntidadBancariaService().obtenerEntidadesBancarias());
        model.addAttribute("tarjetaTipo", serviceManager.getTipoTarjetaService().obtenerTodos());
        model.addAttribute("anioActual", LocalDate.now().getYear());
        return "empleadoPasos/datosEconomicos";
    }

    @PostMapping("/paso4")
    public String procesarPaso4(
            @Validated(Paso4.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
            BindingResult bindingResult,
            HttpSession sesion,
            Model model) {

//        serviceManager.getEmpleadoService().validarMaestrosPaso4(datosEmpleado, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("enviado", true);
            model.addAttribute("entidades", serviceManager.getEntidadBancariaService().obtenerEntidadesBancarias());
            model.addAttribute("tarjetaTipo", serviceManager.getTipoTarjetaService().obtenerTodos());
            model.addAttribute("anioActual", LocalDate.now().getYear());
            return "empleadoPasos/datosEconomicos";
        } else {
            sesion.setAttribute("empleado", datosEmpleado);
            return "redirect:/resumen";
        }
    }


    @GetMapping("/resumen")
    public String resumen(Model model, HttpSession session) {
        EmpleadoDTO empleado = (EmpleadoDTO) session.getAttribute("empleado");
        model.addAttribute("empleadoForm", empleado);
        model.addAttribute("generoService", serviceManager.getGeneroService());
        model.addAttribute("paisService", serviceManager.getPaisService());
        model.addAttribute("tipoDocService", serviceManager.getTipoDocumentoService());
        model.addAttribute("tipoViaService", serviceManager.getTipoViaService());
        model.addAttribute("departamentoService", serviceManager.getDepartamentoService());
        model.addAttribute("entidadBancariaService", serviceManager.getEntidadBancariaService());
        model.addAttribute("tipoTarjetaService", serviceManager.getTipoTarjetaService());
        model.addAttribute("especialidadesService", serviceManager.getEspecialidadesService());

        return "empleadoPasos/resumen";
    }

    @PostMapping("/resumen")
    public String guardarDatosEmpleado(HttpSession session) {

        EmpleadoDTO empleado = (EmpleadoDTO) session.getAttribute("empleado");
        Usuario emailUsuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (emailUsuario.getEmail() == null) {
            // Manejar caso donde no hay usuario autenticado
            return "redirect:/login";
        }

        serviceManager.getEmpleadoService().guardarEmpleado(empleado,emailUsuario.getEmail());
        session.invalidate();

        return "redirect:/inicio";
    }


    @GetMapping("/resetearSesion")
    public String resetearSesion(HttpSession session) {
        //reseteamos la sesión
        session.invalidate();
        return "redirect:/paso1";
    }

    //metodo para no sobrescribir la sesión y poder repintar y mostrar datos en resumen
    @ModelAttribute("datos")
        public EmpleadoDTO getEmpleadoFromSession(HttpSession sesion) {
            EmpleadoDTO empleado = (EmpleadoDTO) sesion.getAttribute("empleado");
            if (empleado == null) {
                empleado = new EmpleadoDTO();
                sesion.setAttribute("empleado", empleado);
            }
            return empleado;
        }
}


//    @RequestMapping(value = "/paso1", method = {RequestMethod.GET, RequestMethod.POST})
//    public String paso1(HttpServletRequest request, HttpSession sesion, Model model,
//                        @Validated(Paso1.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
//                         BindingResult bindingResult
//    ) {
//
//        //con HttpServletRequest accedemos al metodo
//        //si es GET cargará los datos de la sesión o creará una nueva sesión
//        if (request.getMethod().equalsIgnoreCase("GET")) {
//            datosEmpleado = getEmpleadoFromSession(sesion);
//            model.addAttribute("datos", datosEmpleado);
//            model.addAttribute("generos", serviceManager.getGeneroService().obtenerTodos());
//            model.addAttribute("paises", serviceManager.getPaisService().obtenerTodosPaises());
//            return "empleadoPasos/datosPersonales";
//        } else {
//            serviceManager.getEmpleadoService().validarMaestrosPaso1(datosEmpleado, bindingResult);
//            //si hay errores de validación los muestra en el formulario
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("error", bindingResult);
//                model.addAttribute("enviado", true);
//                model.addAttribute("generos", serviceManager.getGeneroService().obtenerTodos());
//                model.addAttribute("paises", serviceManager.getPaisService().obtenerTodosPaises());
//                return "empleadoPasos/datosPersonales";
//            } else {
//                //si  no hay errores redirige al paso dos
//                sesion.setAttribute("empleado", datosEmpleado);
//                return "redirect:/paso2";
//            }
//        }
//    }

//    @RequestMapping(value = "/paso2", method = {RequestMethod.GET, RequestMethod.POST})
//    public String paso2(
//            @Validated(Paso2.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
//            BindingResult bindingResult,
//            HttpServletRequest request,
//            HttpSession sesion,
//            Model model) {
//
//        if (request.getMethod().equalsIgnoreCase("GET")) {
//            datosEmpleado = getEmpleadoFromSession(sesion);
//            model.addAttribute("tipoDoc", serviceManager.getTipoDocumentoService().obtenerTiposDocumento());
//            model.addAttribute("vias", serviceManager.getTipoViaService().obtenerTipoVia());
//            model.addAttribute("datos", datosEmpleado);
//            return "empleadoPasos/datosContacto";
//        } else {
//            serviceManager.getEmpleadoService().validarMaestrosPaso2(datosEmpleado, bindingResult);
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("enviado", true);
//                model.addAttribute("tipoDoc", serviceManager.getTipoDocumentoService().obtenerTiposDocumento());
//                model.addAttribute("vias", serviceManager.getTipoViaService().obtenerTipoVia());
//                return "empleadoPasos/datosContacto";
//            } else {
//                sesion.setAttribute("empleado", datosEmpleado);
//                return "redirect:/paso3";
//            }
//        }
//    }

//    @RequestMapping(value = "/paso3", method = {RequestMethod.GET, RequestMethod.POST})
//    public String paso3(HttpServletRequest request, HttpSession sesion, Model model,
//                        @Validated @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
//                        BindingResult bindingResult
//    ) {
//
//        //con HttpServletRequest accedemos al metodo
//        //si es GET cargará los datos de la sesión o creará una nueva sesión
//        if (request.getMethod().equalsIgnoreCase("GET")) {
//            datosEmpleado = getEmpleadoFromSession(sesion);
//            model.addAttribute("datos", datosEmpleado);
//            model.addAttribute("departamentos", serviceManager.getDepartamentoService().obtenerTodos());
//            model.addAttribute("especialidades", serviceManager.getEspecialidadesService().obtenerEspecialidades());
//            return "empleadoPasos/datosProfesionales";
//        } else {
//            serviceManager.getEmpleadoService().validarMaestrosPaso3(datosEmpleado, bindingResult);
//            //si hay errores de validación los muestra en el formulario
//            if (bindingResult.hasErrors()) {
//                model.addAttribute("errors", bindingResult.getAllErrors());
//                model.addAttribute("enviado", true);
//                model.addAttribute("departamentos", serviceManager.getDepartamentoService().obtenerTodos());
//                model.addAttribute("especialidades", serviceManager.getEspecialidadesService().obtenerEspecialidades());
//                return "empleadoPasos/datosProfesionales";
//            } else {
//                //si  no hay errores redirige al paso dos
//                sesion.setAttribute("empleado", datosEmpleado);
//
//
//                return "redirect:/paso4";
//            }
//        }
//    }

//@RequestMapping(value = "/paso4", method = {RequestMethod.GET, RequestMethod.POST})
//    public String paso4(
//            @Validated(Paso4.class) @ModelAttribute("datos") EmpleadoDTO datosEmpleado,
//            BindingResult bindingResult,
//            HttpServletRequest request,
//            HttpSession sesion,
//            Model model
//    ) {
//
//        //con HttpServletRequest accedemos al metodo
//        //si es GET cargará los datos de la sesión o creará una nueva sesión
//        if (request.getMethod().equalsIgnoreCase("GET")) {
//            datosEmpleado = getEmpleadoFromSession(sesion);
//            model.addAttribute("datos", datosEmpleado);
//            model.addAttribute("entidades", serviceManager.getEntidadBancariaService().obtenerEntidadesBancarias());
//            model.addAttribute("tarjetaTipo", serviceManager.getTipoTarjetaService().obtenerTodos());
//            model.addAttribute("anioActual", LocalDate.now().getYear());
//            return "empleadoPasos/datosEconomicos";
//        } else {
//            serviceManager.getEmpleadoService().validarMaestrosPaso4(datosEmpleado, bindingResult);
//            //si hay errores de validación los muestra en el formulario
//            if (bindingResult.hasErrors()) {
//                System.out.println(bindingResult.getAllErrors());
//                model.addAttribute("errors", bindingResult.getAllErrors());
//                model.addAttribute("enviado", true);
//                model.addAttribute("entidades", serviceManager.getEntidadBancariaService().obtenerEntidadesBancarias());
//                model.addAttribute("tarjetaTipo", serviceManager.getTipoTarjetaService().obtenerTodos());
//                model.addAttribute("anioActual", LocalDate.now().getYear());
//                return "empleadoPasos/datosEconomicos";
//            } else {
//                //si  no hay errores redirige al paso dos
//                sesion.setAttribute("empleado", datosEmpleado);
//                return "redirect:/resumen";
//            }
//        }
//    }
