package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToMany(mappedBy = "nomina", cascade = CascadeType.ALL)
    private List<LineaNomina> lineas = new ArrayList<>();
}