package grupo.a.modulocomun.DTO.filtros;


import grupo.a.modulocomun.DTO.Auxiliares.DireccionDTO;
import grupo.a.modulocomun.Validaciones.paso1.*;
import grupo.a.modulocomun.Validaciones.paso2.Paso2;
import grupo.a.modulocomun.Validaciones.paso2.TipoDocumentoValidation;
import grupo.a.modulocomun.Validaciones.paso2.ValidarDocumento;
import grupo.a.modulocomun.Validaciones.paso2.ValidarDocumento2;
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
@edadValidation2
@ValidarDocumento2
public class PersonaEditarDTO {

    @NotBlank(message = "{validation.notBlank}")
    private String nombre;
    @NotBlank(message = "{validation.notBlank}")
    private String apellido;

    @GeneroValidation
    private Long genero = 2L;

    private int edad;
   @FechaNacValidation
    private String fechaNacimiento;

    @Min(value = 1, message = "Prefijo no válido")
    private int prefijoTel = 34;
    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 9, message = "Debe tener exactamente 9 números")
    private String telefono;
    @Size(min = 9, max = 9, message = "Debe tener exactamente 9 números")
    private String otroTelefono;
    private String email;

    @Valid
    private DireccionDTO direccion= new DireccionDTO();

    @PaisNacimientoValidation
    private Long paisNacimiento = 3L;

    @NotNull(message = "Seleccione un tipo de documento")
    @TipoDocumentoValidation
    private Long tipoDocumento = 1L;

    @NotBlank(message = "Debe introducir un número de documento")
    private String documento;
}