package grupo.a.modulocomun.Entidades.Auxiliares;


import grupo.a.modulocomun.Entidades.Producto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ROPA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRopa extends Producto {

    private String talla;
    private String material;
    private String estacion;
}
