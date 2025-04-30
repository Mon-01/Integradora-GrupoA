package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private UUID id_usuario;
    private String email;
    private String clave;
    private String confirmarClave;
    private boolean bloqueado;
    private String motivoBloqueo;
    private LocalDateTime FechaFinBloqueo;
    private EmpleadoDTO empleado;

}

