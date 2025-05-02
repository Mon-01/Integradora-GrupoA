package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import grupo.a.modulocomun.Validaciones.paso2.Paso2;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    @NotNull(message = "Seleccione un tipo de vía", groups = Paso2.class)
    private Long tipoVia = 1L;

    @NotBlank(message = "El nombre de la vía es obligatorio", groups = Paso2.class)
    private String nombreVia;

    @Min(value = 1, message = "Número inválido", groups = Paso2.class)
    private int numero;

    private int portal;
    private int planta;
    private String puerta;

    @NotBlank(message = "La localidad es obligatoria", groups = Paso2.class)
    private String localidad;

    private String region;

    @NotBlank(message = "El código postal es obligatorio", groups = Paso2.class)
    private String cod_postal;
}