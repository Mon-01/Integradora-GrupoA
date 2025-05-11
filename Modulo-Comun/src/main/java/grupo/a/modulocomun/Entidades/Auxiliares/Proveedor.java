package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_proveedor")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;
}