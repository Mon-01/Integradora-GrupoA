package grupo.a.modulocomun.DTO.filtros;

import lombok.Data;

import java.util.List;

@Data
public class ProductoBusquedaDTO {
    private String descripcion;
    private String proveedor;
    private List<String> categorias;
    private Boolean esPerecedero;
}
