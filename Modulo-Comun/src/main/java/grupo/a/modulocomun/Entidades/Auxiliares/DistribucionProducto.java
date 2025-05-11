package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Producto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistribucionProducto {

    @EmbeddedId
    private DistribucionProductoId id;

    @ManyToOne
    @MapsId("productoId")
    private Producto producto;

    @ManyToOne
    @MapsId("proveedorId")
    private Proveedor proveedor;

    private BigDecimal precio;
    private int unidades;

    @Temporal(TemporalType.DATE)
    private Date fechaFabricacion;
}