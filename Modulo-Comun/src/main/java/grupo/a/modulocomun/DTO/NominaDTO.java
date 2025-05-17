package grupo.a.modulocomun.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NominaDTO {

    private Long id;
    private LocalDate fecha;
    private EmpleadoDTO empleado;
    private List<LineaNominaDTO> lineas;
    private BigDecimal total;

}