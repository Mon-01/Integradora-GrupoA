package grupo.a.modulocomun.Entidades;

import grupo.a.modulocomun.Entidades.Auxiliares.Persona;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Empleado extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_empleado")
    private UUID id_empleado;

    private boolean administrador;
    private String comentarios;
    private String entidadBancaria;
    private String numCuenta;
    private Long salarioAnual;
    private Long comisionAnual;

    @Embedded
    private TarjetaCredito tarjeta;

    //crea una tabla adicional con las especializaciones (strings)
    @ElementCollection
    private List<String> especializaciones = new ArrayList<>();


    //Relaciones
    @OneToOne
    @JoinColumn(name = "id_usuario",foreignKey = @ForeignKey(name = "FK_empleado_usuario_id_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "FK_empleado_departamento_id_dept",nullable = true)
    private Departamento departamento;

}
