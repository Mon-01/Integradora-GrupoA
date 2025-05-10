package grupo.a.modulocomun.DTO.filtros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @AllArgsConstructor
@NoArgsConstructor
public class filtrosNominasDTO {

    private Long id;
    private String nombre;
    private String departamento;
    private LocalDate fecha;
    private BigDecimal salario;
}
