package com.example.aplicacioncorporativa.controller;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.UsuarioDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Usuario;
import com.example.aplicacioncorporativa.Servicios.UsuarioService;
import grupo.a.modulocomun.Servicios.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class controladorMVCCorporativo {

    @Autowired
    private EmpleadoService empleadoService;

    private final UsuarioService usuarioService;
    private final HttpSession session;
//    private final EmpleadoService empleadoService;
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

        // Creamos un DTO con el id del usuario real
        UsuarioDTO dto_id = new UsuarioDTO();
        dto_id.setId_usuario(usuario.get().getId_usuario());

        model.addAttribute("dto", dto_id);
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "corporativo/contrasenia";
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
    public String areaPersonal(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        String email = (String) session.getAttribute("emailTemporal");
        usuarioDTO.setEmail(email);

        // Obtener la imagen en base64
        String imagenBase64 = empleadoService.obtenerImagenBase64PorCorreo(email);

        // Agregar la imagen al modelo
        model.addAttribute("imagenBase64", imagenBase64);

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

    @GetMapping("/cambiar-clave/{id}")
    public String mostrarFormularioCambio(@PathVariable("id") UUID id_usuario, Model model) {
        UsuarioDTO dto = new UsuarioDTO();
        model.addAttribute("usuarioDTO", dto);
        dto.setId_usuario(id_usuario);
        return "corporativo/cambiarContra";
    }

    @GetMapping("/nomina/{empleadoId}")
    public String mostrarDetalleNomina(@PathVariable Long empleadoId, Model model) {
        model.addAttribute("empleadoId", empleadoId); // Pasa el ID del empleado a la vista.
        return "/corporativo/detalleNominaEmpleado"; // Retorna la vista "detalle-nomina.html".
    }

}

