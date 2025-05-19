package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UsuaroServiceComun {

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario insertarBlock(Empleado empleado){
        Usuario user = new Usuario();
        user.setEmpleado(empleadoRepository.findById(empleado.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado al empleado con id: " + empleado.getId_empleado())));
        user.setBloqueado(true);
        user.setEmail(empleado.getEmail());
        user.setMotivoBloqueo("sopla gaitas");
        user.setClave("bloqueado123");
        user.setFechaFinBloqueo(LocalDateTime.now().plusMinutes(10));
        user.setRespuestaSecreta("Madrid");
        usuarioRepository.save(user);
        return user;
    }

    public Usuario insertarUnBlock(Empleado empleado){
        Usuario user = new Usuario();
        user.setEmpleado(empleadoRepository.findById(empleado.getId_empleado())
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado al empleado con id: " + empleado.getId_empleado())));
        user.setBloqueado(false);
        user.setEmail(empleado.getEmail());
        user.setClave("NoBloqueado123");
        user.setRespuestaSecreta("Barcelona");
        usuarioRepository.save(user);
        return user;
    }
}
