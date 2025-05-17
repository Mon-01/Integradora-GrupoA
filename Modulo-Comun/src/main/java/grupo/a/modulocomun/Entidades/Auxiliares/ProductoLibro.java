package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Producto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("LIBRO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoLibro extends Producto {
    private String titulo;
    private String autor;
    private String editorial;
    private String tapa;
    private int numeroPaginas;
    private boolean segundaMano;
}