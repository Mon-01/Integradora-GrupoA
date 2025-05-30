package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    @Query("SELECT d FROM Departamento d WHERE d.nombredept = :nombre")
    Optional<Departamento> findDepartamentoByNombre(@Param("nombre") String nombre);


    @Query("SELECT DISTINCT d.nombredept FROM Departamento d ORDER BY d.nombredept")
    List<String> findDistinctNombresDepartamento();
}
