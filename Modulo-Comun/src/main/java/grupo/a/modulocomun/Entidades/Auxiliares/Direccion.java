package grupo.a.modulocomun.Entidades.Auxiliares;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
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


