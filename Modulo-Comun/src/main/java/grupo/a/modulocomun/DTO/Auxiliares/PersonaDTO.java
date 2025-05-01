package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Validaciones.paso1.FechaNacValidation;
import grupo.a.modulocomun.Validaciones.paso1.Paso1;
import grupo.a.modulocomun.Validaciones.paso1.edadValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@edadValidation(groups = Paso1.class)
public class PersonaDTO {

    @NotBlank(message = "{validation.notBlank}",groups = Paso1.class)
    private String nombre;
    @NotBlank(message = "{validation.notBlank}",groups = Paso1.class)
    private String apellido;

    private Long genero = 2L;

    private int edad;
    @FechaNacValidation(groups = Paso1.class)
    private String fechaNacimiento;

    private int prefijoTel = 34;
    private String telefono;
    private String otroTelefono;
    private String email;

    private DireccionDTO direccion;

    private Long paisNacimiento = 3L;

    private Long tipoDocumento = 1L;
    private String documento;
}
