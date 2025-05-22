//WHY ALWAYS ME
package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Validaciones.paso1.*;
import grupo.a.modulocomun.Validaciones.paso2.Paso2;
import grupo.a.modulocomun.Validaciones.paso2.TipoDocumentoValidation;
import grupo.a.modulocomun.Validaciones.paso2.ValidarDocumento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@edadValidation(groups = Paso1.class)
@ValidarDocumento(groups = Paso2.class)
public class PersonaDTO {

    @NotBlank(message = "{validation.notBlank}",groups = Paso1.class)
    private String nombre;
    @NotBlank(message = "{validation.notBlank}",groups = Paso1.class)
    private String apellido;

    @GeneroValidation(groups = Paso1.class)
    private Long genero = 2L;

    private int edad;
    @FechaNacValidation(groups = Paso1.class)
    private String fechaNacimiento;

    @Min(value = 1, message = "Prefijo no válido", groups = Paso2.class)
    private int prefijoTel = 34;
    @NotBlank(message = "El teléfono es obligatorio", groups = Paso2.class)
    @Size(min = 9, max = 9, message = "Debe tener exactamente 9 números", groups = Paso2.class)
    private String telefono;
    @Size(min = 9, max = 9, message = "Debe tener exactamente 9 números", groups = Paso2.class)
    private String otroTelefono;
    private String email;

    @Valid
    private DireccionDTO direccion= new DireccionDTO();

    @PaisNacimientoValidation(groups = Paso1.class)
    private Long paisNacimiento = 3L;

    @NotNull(message = "Seleccione un tipo de documento", groups = Paso2.class)
    @TipoDocumentoValidation(groups = Paso2.class)
    private Long tipoDocumento = 1L;

    @NotBlank(message = "Debe introducir un número de documento", groups = Paso2.class)
    private String documento;
}
