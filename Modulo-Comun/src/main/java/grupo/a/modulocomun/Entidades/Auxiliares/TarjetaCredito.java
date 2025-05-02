package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.TipoTarjeta;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TarjetaCredito {

    @ManyToOne
    @JoinColumn(name = "tipo_tarjeta_id")
    private TipoTarjeta tipo;

    private String numero;
    @Embedded
    private Caducidad caducidad;
    private String cvc;
}
