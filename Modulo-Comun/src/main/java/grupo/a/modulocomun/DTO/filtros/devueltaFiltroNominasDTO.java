package grupo.a.modulocomun.DTO.filtros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class devueltaFiltroNominasDTO {

    private Long idUsuario;
    private String nombre;
    private LocalDate fecha;
    private BigDecimal salario;
}
