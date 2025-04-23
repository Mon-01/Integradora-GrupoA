package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Caducidad {
    private int anio;
    private int mes;
}
