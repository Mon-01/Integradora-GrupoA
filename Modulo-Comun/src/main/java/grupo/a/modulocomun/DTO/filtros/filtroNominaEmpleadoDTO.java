package grupo.a.modulocomun.DTO.filtros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor
@AllArgsConstructor

public class filtroNominaEmpleadoDTO {
    private String concepto;
    private LocalDate fecha;
}
