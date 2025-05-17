package grupo.a.modulocomun.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id",nullable = false)
    @JsonIgnoreProperties("nominas")
    private Empleado empleado;

    @OneToMany(mappedBy = "nomina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaNomina> lineas = new ArrayList<>();
    public void agregarLinea(LineaNomina linea) {
        this.lineas.add(linea);
        linea.setNomina(this);
    }

    public Nomina(LocalDate fecha, Empleado empleado, List<LineaNomina> lineas) {
        this.fecha = fecha;
        this.empleado = empleado;
        this.lineas = lineas;
    }
}