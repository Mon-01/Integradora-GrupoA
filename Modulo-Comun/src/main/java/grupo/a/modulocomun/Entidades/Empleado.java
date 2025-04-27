package grupo.a.modulocomun.Entidades;

import grupo.a.modulocomun.Entidades.Auxiliares.DatosBancarios;
import grupo.a.modulocomun.Entidades.Auxiliares.Persona;
import grupo.a.modulocomun.Entidades.Maestros.Especialidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="empleados")
public class Empleado extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_empleado")
    private UUID id_empleado;

    private boolean administrador;
    private String comentarios;
    private Long salarioAnual;
    private Long comisionAnual;

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
    private List<Especialidades> especialidades = new ArrayList<>();



    //Relaciones
    @OneToOne
    @JoinColumn(name = "id_usuario",foreignKey = @ForeignKey(name = "FK_empleado_usuario_id_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_empleado_departamento_id_dept",nullable = true)
    private Departamento departamento;

}
