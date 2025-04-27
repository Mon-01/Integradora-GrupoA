package grupo.a.modulocomun.Repositorios.Maestros;

import grupo.a.modulocomun.Entidades.Maestros.Especialidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadesRepository extends JpaRepository<Especialidades, Long> {
}
