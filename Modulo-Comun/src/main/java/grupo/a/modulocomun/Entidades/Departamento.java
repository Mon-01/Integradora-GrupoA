package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "departamentos")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_departamento")
    private UUID id_dept;

    private String nombre_dept;
    private String cod;
    private String loc;
    private BigDecimal presupuesto;

    public Departamento(String nombre_dept, String cod, String loc, BigDecimal presupuesto) {
        this.nombre_dept = nombre_dept;
        this.cod = cod;
        this.loc = loc;
        this.presupuesto = presupuesto;
    }
}
