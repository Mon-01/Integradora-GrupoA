package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TarjetaCredito {
    private String tipo;
    private String numero;
  //  private Caducidad caducidad;
    private int cvc;
}
