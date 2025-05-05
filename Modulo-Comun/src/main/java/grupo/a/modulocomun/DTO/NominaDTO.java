package grupo.a.modulocomun.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class NominaDTO {

    private Long id;
    private LocalDate fecha;
    private EmpleadoDTO empleado;
    private List<LineaNominaDTO> lineas;
}