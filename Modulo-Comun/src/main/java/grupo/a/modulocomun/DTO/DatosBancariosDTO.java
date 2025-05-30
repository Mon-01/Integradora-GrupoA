package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.Auxiliares.TarjetaCreditoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Validaciones.paso4.EntidadBancariaValidation;
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

    @EntidadBancariaValidation(groups = Paso4.class)
    private Long entidadBancaria = 1L;
    private String numCuenta = "ES91 2100 0418 4502 0005 1332";

    @Valid
    private TarjetaCreditoDTO tarjeta = new TarjetaCreditoDTO();
}
