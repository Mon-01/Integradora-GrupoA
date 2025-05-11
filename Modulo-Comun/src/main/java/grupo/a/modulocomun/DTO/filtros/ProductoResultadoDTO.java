package grupo.a.modulocomun.DTO.filtros;


import lombok.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResultadoDTO {
    private String descripcion;
    private BigDecimal precio;
    private String categoriaPrincipal;
    private int unidades;
    private String proveedor;
}
