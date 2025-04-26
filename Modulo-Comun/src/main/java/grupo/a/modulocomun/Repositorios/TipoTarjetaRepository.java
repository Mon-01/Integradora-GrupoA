package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.TipoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTarjetaRepository extends JpaRepository<TipoTarjeta, Integer> {
}
