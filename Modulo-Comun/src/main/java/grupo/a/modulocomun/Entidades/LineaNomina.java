package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaNomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private BigDecimal porcentaje;
    private BigDecimal importeFijo;
    private Boolean esDevengo; // true = devengo (suma), false = deducción (resta)
    private BigDecimal importe; // Campo calculado

    @ManyToOne
    @JoinColumn(name = "nomina_id", nullable = false)
    private Nomina nomina;
}