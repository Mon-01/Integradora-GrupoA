package grupo.a.modulocomun.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDTO {
    private Long id_dept = 1L;
    private String nombre_dept;
    private String cod;
    private String loc;
    private BigDecimal presupuesto;
}
