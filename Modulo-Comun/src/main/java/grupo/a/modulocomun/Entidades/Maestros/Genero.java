package grupo.a.modulocomun.Entidades.Maestros;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grupo.a.modulocomun.Entidades.Auxiliares.Persona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Genero(String nombre) {
        this.nombre = nombre;
    }

}
