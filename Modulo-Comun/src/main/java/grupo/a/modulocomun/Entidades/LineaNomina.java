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
    private BigDecimal deducciones;
    private BigDecimal devengos;
    private BigDecimal importe;

    @ManyToOne
    @JoinColumn(name = "nomina_id",nullable = false)
    private Nomina nomina;

    public LineaNomina(String descripcion,BigDecimal importe) {
        this.importe = importe;
        this.descripcion = descripcion;
    }
}