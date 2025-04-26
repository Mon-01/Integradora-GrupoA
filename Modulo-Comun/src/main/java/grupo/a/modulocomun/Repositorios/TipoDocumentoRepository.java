package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
}
