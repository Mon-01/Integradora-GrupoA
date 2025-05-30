package grupo.a.modulocomun.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_departamento")
    private Long id_dept;

    private String nombredept;
    private String cod;
    private String loc;
    private BigDecimal presupuesto;

    public Departamento(String nombre_dept, String cod, String loc, BigDecimal presupuesto) {
        this.nombredept = nombre_dept;
        this.cod = cod;
        this.loc = loc;
        this.presupuesto = presupuesto;
    }
}
