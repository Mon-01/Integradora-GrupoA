package grupo.a.modulocomun.Entidades;

import grupo.a.modulocomun.Entidades.Auxiliares.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data @AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_producto")
    private Long id_producto;

    private String nombre;
    private BigDecimal precio;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
