package grupo.a.modulocomun.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAdministrador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PK_usuario_administrador")
    private UUID id_usuario_administrador;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String clave;
}
