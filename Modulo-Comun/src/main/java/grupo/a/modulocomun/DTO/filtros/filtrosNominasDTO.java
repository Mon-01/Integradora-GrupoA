package grupo.a.modulocomun.DTO.filtros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor
@NoArgsConstructor
public class filtrosNominasDTO {

    private String nombre;
    private LocalDate fecha;
}
