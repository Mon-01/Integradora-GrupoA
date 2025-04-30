package com.example.aplicacioncorporativa.controller;

import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Usuario;
import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class controladorMVCCorporativo {

    private final UsuarioService usuarioService;
    private final HttpSession session;
    private final EmpleadoService empleadoService;
    private int intentosClave = 0;

    public controladorMVCCorporativo(UsuarioService usuarioService, HttpSession session, EmpleadoService empleadoService) {
        this.usuarioService = usuarioService;
        this.session = session;
        this.empleadoService = empleadoService;
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

        if(usuarioService.isBloqueado(usuario.get())){

            model.addAttribute("bloqueado", true);
            model.addAttribute("usuario", usuario.get());
            return "corporativo/inicio";
        }

        session.setAttribute("emailTemporal", usuarioDTO.getEmail());
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "corporativo/contrasenia.html";
    }

    @PostMapping("/password")
    public String procesarPassword(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, Model model) {
        String email = (String) session.getAttribute("emailTemporal");
        usuarioDTO.setEmail(email);

        Optional<Usuario> usuario = usuarioService.buscarPorEmail(Optional.of(usuarioDTO));
        if (usuario.isEmpty() || !usuarioService.comprobarPassword(usuario.get(), usuarioDTO.getClave())) {
            model.addAttribute("error", "Contraseña incorrecta.");
            intentosClave++;
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            if(intentosClave >= 3){
                usuarioService.bloquearUsuario(usuario.get().getId_usuario(), "Contraseña incorrecta 3 veces", LocalDateTime.now().plusMinutes(15));
                usuarioService.actualizarUsuario(usuario.get());
                model.addAttribute("bloqueado", true);
                return "redirect:/login";
            }
            return "corporativo/contrasenia.html";
        }

        session.setAttribute("usuarioLogueado", usuario.get());
        return "redirect:/inicio";
    }
    @GetMapping("/inicio")
    public String areaPersonal(Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/login"; // Si no hay usuario en sesión, lo mandamos al login
        }

        // Contador de accesos de este navegador
        Integer contadorSesion = (Integer) session.getAttribute("contadorSesion");
        if (contadorSesion == null) {
            contadorSesion = 1;
        } else {
            contadorSesion++;
        }
        session.setAttribute("contadorSesion", contadorSesion);

        // Contador total de accesos (guardado en toda la aplicación)
        Map<String, Integer> contadorGlobal = (Map<String, Integer>) session.getServletContext().getAttribute("contadorGlobal");
        if (contadorGlobal == null) {
            contadorGlobal = new HashMap<>();
        }
        int contadorTotal = contadorGlobal.getOrDefault(usuarioLogueado.getEmail(), 0) + 1;
        contadorGlobal.put(usuarioLogueado.getEmail(), contadorTotal);

        session.getServletContext().setAttribute("contadorGlobal", contadorGlobal);

        // Enviamos datos a la vista
        model.addAttribute("usuario",usuarioLogueado);
        model.addAttribute("usuarioEmail", usuarioLogueado.getEmail());
        model.addAttribute("contadorSesion", contadorSesion);
        model.addAttribute("contadorTotal", contadorTotal);

        return "corporativo/areaPersonal";
    }



    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model) {
        Optional<Empleado> empleado = empleadoService.obtenerEmpleadoPorId(id);
        if (empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "corporativo/detalle";
        } else {
            return "corporativo/404"; // o puedes redirigir a una página de error genérica
        }
    }

}

