package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.DTO.ProductoDTO;
import lombok.Data;

import java.util.List;

@Data
public class CatalogoProductosDTO {
    private String proveedor;

    private List<ProductoDTO> productos;
}