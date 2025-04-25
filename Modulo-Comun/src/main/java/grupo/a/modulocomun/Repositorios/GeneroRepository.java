package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
