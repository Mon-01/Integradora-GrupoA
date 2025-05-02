package grupo.a.modulocomun.Entidades.Auxiliares;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Caducidad {
    private int anio = LocalDate.now().getYear();
    private int mes = 1;
}

