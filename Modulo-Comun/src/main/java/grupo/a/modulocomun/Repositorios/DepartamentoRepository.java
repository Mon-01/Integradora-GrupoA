package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}
