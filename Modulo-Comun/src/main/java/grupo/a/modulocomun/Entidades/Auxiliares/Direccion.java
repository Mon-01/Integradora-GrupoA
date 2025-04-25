package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Direccion {
    private String tipoVia;
    private String nombreVia;
    private int numero;
    private int portal;
    private int planta;
    private String puerta;
    private String localidad;
    private String region;
    private String cod_postal;
}


