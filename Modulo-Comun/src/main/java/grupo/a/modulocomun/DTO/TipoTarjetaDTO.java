package grupo.a.modulocomun.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTarjetaDTO {
    private Long id;
    private String nombre;

    public TipoTarjetaDTO(String nombre) {
        this.nombre = nombre;
    }
}