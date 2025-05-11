package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Auxiliares.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}