package grupo.a.modulocomun.Entidades.Maestros;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entidadesBancarias")
public class EntidadBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pais;
    private String nombre;
    private String cod_entidad;

    public EntidadBancaria(String nombre) {
        this.nombre = nombre;
    }

    public EntidadBancaria(String pais, String nombre, String cod_entidad) {
        this.pais = pais;
        this.nombre = nombre;
        this.cod_entidad = cod_entidad;
    }
}
