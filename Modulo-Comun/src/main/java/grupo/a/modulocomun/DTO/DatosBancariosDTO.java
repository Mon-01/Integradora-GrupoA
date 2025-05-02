package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.Auxiliares.TarjetaCreditoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Validaciones.paso4.Paso4;
import grupo.a.modulocomun.Validaciones.paso4.ibanValidation;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
@ibanValidation(groups = Paso4.class)
public class DatosBancariosDTO {

    private Long id;
    private Long entidadBancaria = 1L;
    private String numCuenta;

    @Valid
    private TarjetaCreditoDTO tarjeta;
}
