package com.example.aplicacioncorporativa.controller;

import com.example.aplicacioncorporativa.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Servicios.DatosAuxiliaresService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class controladorEmpleadoPasos {

    @Autowired
    private DatosAuxiliaresService datosAuxiliaresService;

    @RequestMapping(value = "/paso1", method = {RequestMethod.GET, RequestMethod.POST})
    public String paso1(@ModelAttribute("datos") EmpleadoDTO datosEmpleado,HttpServletRequest request, HttpSession sesion, Model model,
                        BindingResult bindingResult
    ) {

        //con HttpServletRequest accedemos al metodo
        //si es GET cargará los datos de la sesión o creará una nueva sesión
        if (request.getMethod().equalsIgnoreCase("GET")) {
            datosEmpleado = getEmpleadoFromSession(sesion);
            model.addAttribute("datos", datosEmpleado);
            model.addAttribute("generos", datosAuxiliaresService.getGeneroService().obtenerTodos());
            model.addAttribute("paises", datosAuxiliaresService.getPaisService().obtenerTodosPaises());
            return "empleadoPasos/datosPersonales";
        } else {
            //si hay errores de validación los muestra en el formulario
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("enviado", true);
                return "empleadoPasos/datosPersonales";
            } else {
                //si  no hay errores redirige al paso dos
                sesion.setAttribute("empleado", datosEmpleado);
                return "redirect:/paso2";
            }
        }
    }

    @RequestMapping(value = "/paso2", method = {RequestMethod.GET, RequestMethod.POST})
    public String paso2(@ModelAttribute("datos") EmpleadoDTO datosEmpleado,HttpServletRequest request, HttpSession sesion, Model model,
                        BindingResult bindingResult
    ) {

        //con HttpServletRequest accedemos al metodo
        //si es GET cargará los datos de la sesión o creará una nueva sesión
        if (request.getMethod().equalsIgnoreCase("GET")) {
            datosEmpleado = getEmpleadoFromSession(sesion);
            model.addAttribute("tipoDoc", datosAuxiliaresService.getTipoDocumentoService().obtenerTiposDocumento());
            model.addAttribute("vias", datosAuxiliaresService.getTipoViaService().obtenerTipoVia());
            model.addAttribute("datos", datosEmpleado);
            return "empleadoPasos/datosContacto";
        } else {
            //si hay errores de validación los muestra en el formulario
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("enviado", true);
                return "empleadoPasos/datosContacto";
            } else {
                //si  no hay errores redirige al paso dos
                sesion.setAttribute("empleado", datosEmpleado);
                return "redirect:/paso3";
            }
        }
    }

    @RequestMapping(value = "/paso3", method = {RequestMethod.GET, RequestMethod.POST})
    public String paso3(@ModelAttribute("datos") EmpleadoDTO datosEmpleado,HttpServletRequest request, HttpSession sesion, Model model,
                        BindingResult bindingResult
    ) {

        //con HttpServletRequest accedemos al metodo
        //si es GET cargará los datos de la sesión o creará una nueva sesión
        if (request.getMethod().equalsIgnoreCase("GET")) {
            datosEmpleado = getEmpleadoFromSession(sesion);
            model.addAttribute("datos", datosEmpleado);
            model.addAttribute("departamentos", datosAuxiliaresService.getDepartamentoService().obtenerTodos());
            model.addAttribute("especialidades", datosAuxiliaresService.getEspecialidadesService().obtenerEspecialidades());
            return "empleadoPasos/datosProfesionales";
        } else {
            //si hay errores de validación los muestra en el formulario
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("enviado", true);
                return "empleadoPasos/datosProfesionales";
            } else {
                //si  no hay errores redirige al paso dos
                sesion.setAttribute("empleado", datosEmpleado);
                return "redirect:/paso4";
            }
        }
    }

    @RequestMapping(value = "/paso4", method = {RequestMethod.GET, RequestMethod.POST})
    public String paso4(@ModelAttribute("datos") EmpleadoDTO datosEmpleado,HttpServletRequest request, HttpSession sesion, Model model,
                        BindingResult bindingResult
    ) {

        //con HttpServletRequest accedemos al metodo
        //si es GET cargará los datos de la sesión o creará una nueva sesión
        if (request.getMethod().equalsIgnoreCase("GET")) {
            datosEmpleado = getEmpleadoFromSession(sesion);
            model.addAttribute("datos", datosEmpleado);
            model.addAttribute("entidades",datosAuxiliaresService.getEntidadBancariaService().obtenerEntidadesBancarias());
            model.addAttribute("tarjetaTipo",datosAuxiliaresService.getTipoTarjetaService().obtenerTodos());
            return "empleadoPasos/datosEconomicos";
        } else {
            //si hay errores de validación los muestra en el formulario
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("enviado", true);
                return "empleadoPasos/datosEconomicos";
            } else {
                //si  no hay errores redirige al paso dos
                sesion.setAttribute("empleado", datosEmpleado);
                return "redirect:/resumen";
            }
        }
    }

    @GetMapping("/resumen")
    public String resumen(Model model, HttpSession session) {
        EmpleadoDTO empleado = (EmpleadoDTO) session.getAttribute("empleado");
        model.addAttribute("empleadoForm", empleado);
        return "empleadoPasos/resumen";
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
