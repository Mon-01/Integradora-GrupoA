package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "tiposVia")
public class TipoVia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public TipoVia(String nombre) {
        this.nombre = nombre;
    }
}
