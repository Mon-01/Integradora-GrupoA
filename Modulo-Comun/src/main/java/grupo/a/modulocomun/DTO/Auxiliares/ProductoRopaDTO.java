package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.DTO.ProductoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductoRopaDTO extends ProductoDTO {
    private String talla;
    private String material;
    private String estacion;
}
