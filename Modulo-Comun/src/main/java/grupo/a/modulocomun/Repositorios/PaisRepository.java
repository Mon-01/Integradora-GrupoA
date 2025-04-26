package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}
