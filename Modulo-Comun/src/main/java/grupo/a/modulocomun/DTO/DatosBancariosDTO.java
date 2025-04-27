package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.Auxiliares.TarjetaCreditoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosBancariosDTO {

    private Long id;
    private String entidadBancaria;
    private String numCuenta;

    private TarjetaCreditoDTO tarjeta;
}
