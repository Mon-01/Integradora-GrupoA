package grupo.a.modulocomun.Repositorios.Maestros;

import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoViaRepository extends JpaRepository<TipoVia, Long> {
}
