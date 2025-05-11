package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.DTO.ProductoDTO;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductoMuebleDTO extends ProductoDTO {
    private int ancho;
    private int profundo;
    private int alto;
    private List<String> colores;
}