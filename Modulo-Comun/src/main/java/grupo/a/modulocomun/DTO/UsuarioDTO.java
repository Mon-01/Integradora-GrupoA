package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private UUID id_usuario;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    @NotNull
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @NotNull
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String clave;

    @NotBlank(message = "Debe confirmar la contraseña")
    @NotNull
    private String confirmarClave;

    private boolean bloqueado;
    private String motivoBloqueo;
    private LocalDateTime FechaFinBloqueo;
    private EmpleadoDTO empleado;

    @NotBlank(message = "La respuesta secreta no puede estar vacía")
    @NotNull
    private String respuestaSecreta;

}

