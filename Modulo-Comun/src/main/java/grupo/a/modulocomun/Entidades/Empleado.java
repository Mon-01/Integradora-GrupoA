package grupo.a.modulocomun.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grupo.a.modulocomun.Entidades.Auxiliares.Persona;
import grupo.a.modulocomun.Entidades.Maestros.Especialidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="empleados")
public class Empleado extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_empleado")
    private Long id_empleado;

    private boolean administrador;
    private String comentarios;
    private BigDecimal salarioAnual;
    private BigDecimal comisionAnual;

    @Column(name = "fecha_alta")
    private LocalDate fecha_alta;

    //al guardar un empleado también guarda los datos bancarios
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "datos_bancarios_id", referencedColumnName = "id")
    private DatosBancarios datosBancarios;

    @ManyToMany
    @JoinTable(
            name = "empleado_especialidades",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private List<Especialidades> especializaciones = new ArrayList<>();

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("empleado") // Evita recursión
    private List<Nomina> nominas = new ArrayList<>();



    //Relaciones
    @OneToOne
    @JoinColumn(name = "id_usuario",foreignKey = @ForeignKey(name = "FK_empleado_usuario_id_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_empleado_departamento_id_dept",nullable = true)
    private Departamento departamento;


}
