package grupo.a.modulocomun.Repositorios.Maestros;

import grupo.a.modulocomun.Entidades.Maestros.TipoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTarjetaRepository extends JpaRepository<TipoTarjeta, Long> {
}
