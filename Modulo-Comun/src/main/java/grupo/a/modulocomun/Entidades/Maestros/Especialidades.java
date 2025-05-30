package grupo.a.modulocomun.Entidades.Maestros;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
public class Especialidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Especialidades(String nombre) {
        this.nombre = nombre;
    }
}
