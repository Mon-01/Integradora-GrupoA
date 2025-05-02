package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Usuario;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional <Usuario> findByEmail(String email);

    @Query(value = "SELECT email FROM usuario ORDER BY PK_usuario DESC LIMIT 5", nativeQuery = true)
    List<String> findLast5Emails();
}
