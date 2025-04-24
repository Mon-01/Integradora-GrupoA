package com.example.aplicacioncorporativa.controller;

import com.example.aplicacioncorporativa.DTO.EmpleadoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class controladorEmpleadoPasos {

    @RequestMapping(value = "/paso1", method = {RequestMethod.GET, RequestMethod.POST})
    public String paso1(@ModelAttribute("datos") EmpleadoDTO datosEmpleado,HttpServletRequest request, HttpSession sesion, Model model,
                        BindingResult bindingResult
    ) {

        //con HttpServletRequest accedemos al metodo
        //si es GET cargará los datos de la sesión o creará una nueva sesión
        if (request.getMethod().equalsIgnoreCase("GET")) {
            datosEmpleado = getEmpleadoFromSession(sesion);
            model.addAttribute("datos", datosEmpleado);
            return "empleadoPasos/datosPersonales";
        } else {
            //si hay errores de validación los muestra en el formulario
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("enviado", true);
                return "datosPersonales";
            } else {
                //si  no hay errores redirige al paso dos
                sesion.setAttribute("empleado", datosEmpleado);
                return "redirect:/paso2";
            }
        }
    }

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
