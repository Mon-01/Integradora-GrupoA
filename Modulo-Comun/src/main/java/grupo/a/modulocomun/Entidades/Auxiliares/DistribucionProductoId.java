package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistribucionProductoId implements Serializable {
    private Long productoId;
    private Long proveedorId;
}