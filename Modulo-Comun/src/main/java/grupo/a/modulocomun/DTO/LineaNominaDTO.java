package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.NominaDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineaNominaDTO {
    private Long id;
    private String concepto;
    private double cantidad;
    private NominaDTO nomina;
}