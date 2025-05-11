package grupo.a.modulocomun.DTO.Auxiliares;

import grupo.a.modulocomun.DTO.ProductoDTO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductoLibroDTO extends ProductoDTO {
    private String titulo;
    private String autor;
    private String editorial;
    private String tapa;
    private int numeroPaginas;
    private boolean segundaMano;
}