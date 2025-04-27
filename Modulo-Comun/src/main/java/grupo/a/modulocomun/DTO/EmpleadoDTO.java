package grupo.a.modulocomun.DTO;
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
public class EmpleadoDTO extends Persona {
    private String comentarios;
    private String entidadBancaria;
    private String numCuenta;
    private Long salarioAnual;
    private Long comisionAnual;
    private TarjetaCredito tarjeta;
    private String departamento;
    private List<String> especializaciones = new ArrayList<>();
}