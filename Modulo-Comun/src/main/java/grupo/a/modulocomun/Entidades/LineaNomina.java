package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LineaNomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private BigDecimal importe;

    @ManyToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;
}