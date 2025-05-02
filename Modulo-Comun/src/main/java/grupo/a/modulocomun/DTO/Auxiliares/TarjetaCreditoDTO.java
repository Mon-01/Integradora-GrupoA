package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Entidades.Auxiliares.Caducidad;
import grupo.a.modulocomun.Entidades.Maestros.TipoTarjeta;
import grupo.a.modulocomun.Validaciones.paso4.Paso4;
import grupo.a.modulocomun.Validaciones.paso4.cvcValidation;
import grupo.a.modulocomun.Validaciones.paso4.numeroValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Valid
public class TarjetaCreditoDTO {
    private Long tipo = 1L;

    @numeroValidation(groups = Paso4.class)
    private String numero;
    private Caducidad caducidad;

    @cvcValidation(groups = Paso4.class)
    private String cvc;
}
