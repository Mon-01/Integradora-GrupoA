package grupo.a.modulocomun.Entidades;

import grupo.a.modulocomun.Entidades.Auxiliares.Categoria;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_producto")
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_producto")
    private Long id_producto;

    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private int valoracion;
    private String marca;
    private Boolean esPerecedero;

    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
}
