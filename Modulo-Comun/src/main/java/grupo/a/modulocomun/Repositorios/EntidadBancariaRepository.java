package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.EntidadBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadBancariaRepository extends JpaRepository<EntidadBancaria, Long> {
}
