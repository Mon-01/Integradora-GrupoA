package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Producto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("MUEBLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoMueble extends Producto {
    private int ancho;
    private int profundo;
    private int alto;

    @ElementCollection
    private List<String> colores;
}