package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private TipoVia tipoVia;

    private String nombreVia;
    private int numero;
    private int portal;
    private int planta;
    private String puerta;
    private String localidad;
    private String region;
    private String cod_postal;
}
