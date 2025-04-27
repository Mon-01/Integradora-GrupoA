package grupo.a.modulocomun.Entidades.Auxiliares;

import grupo.a.modulocomun.Entidades.Maestros.Genero;
import grupo.a.modulocomun.Entidades.Maestros.Pais;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
* dos opciones para que usuario y empleado hereden de persona
* @Inheritance(strategy = InheritanceType.JOINED)
* */
@AllArgsConstructor @NoArgsConstructor @Data
@MappedSuperclass
public class Persona {
    private String nombre;
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    private int edad;
    private String fecha_nacimiento;
    private int prefijoTel;
    private String telefono;
    private String otroTelefono;
    private String email;

    @Embedded
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "pais_nacimiento_id")
    private Pais paisNacimiento;

    private String tipoDocumento;
    private String documento;

}
