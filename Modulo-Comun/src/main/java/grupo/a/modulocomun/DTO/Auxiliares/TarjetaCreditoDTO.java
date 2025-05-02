package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.TipoTarjeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TarjetaCreditoDTO {
    private Long tipo = 1L;

    private String numero;
    //    private Caducidad caducidad;
    private int cvc;
}
