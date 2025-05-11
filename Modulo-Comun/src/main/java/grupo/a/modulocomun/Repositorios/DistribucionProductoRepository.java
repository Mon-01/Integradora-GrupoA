package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Auxiliares.DistribucionProducto;
import grupo.a.modulocomun.Entidades.Auxiliares.DistribucionProductoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistribucionProductoRepository extends JpaRepository<DistribucionProducto, DistribucionProductoId> {
    Optional<DistribucionProducto> findById(DistribucionProductoId id);
}