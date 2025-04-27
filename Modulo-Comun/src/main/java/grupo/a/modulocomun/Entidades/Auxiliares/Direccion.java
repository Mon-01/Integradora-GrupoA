package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Direccion {
    @ManyToOne
    @JoinColumn(name = "tipo_via_id")
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


