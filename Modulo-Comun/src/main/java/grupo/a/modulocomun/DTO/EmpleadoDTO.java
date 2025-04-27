package grupo.a.modulocomun.DTO;
import grupo.a.modulocomun.DTO.Auxiliares.PersonaDTO;
import grupo.a.modulocomun.DTO.Auxiliares.TarjetaCreditoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Persona;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO extends PersonaDTO {
    private String comentarios;
    private Long salarioAnual;
    private Long comisionAnual;
    private DatosBancariosDTO datosBancarios;
    private Long departamento;
    private List<Long> especializaciones = new ArrayList<>();
}