package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntidadBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public EntidadBancaria(String nombre) {
        this.nombre = nombre;
    }
}
