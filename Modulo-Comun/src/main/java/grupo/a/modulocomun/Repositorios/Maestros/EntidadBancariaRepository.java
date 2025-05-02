package grupo.a.modulocomun.Repositorios.Maestros;

import grupo.a.modulocomun.Entidades.Maestros.EntidadBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadBancariaRepository extends JpaRepository<EntidadBancaria, Long> {
}
