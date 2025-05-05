package grupo.a.modulocomun.DTO;

import grupo.a.modulocomun.DTO.NominaDTO;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaNominaDTO {
    private Long id;
    private String concepto;
    private BigDecimal cantidad;
}