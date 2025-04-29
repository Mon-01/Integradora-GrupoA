package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    /*
    List <Empleado> findByNombreContainingIgnoreCaseAndDepartamento_Nombre_deptAndAndSalarioAnualGreaterThanEqual(String nombre, String nombreDept, Long salarioMinimo)

    ;

     */
    @Query("SELECT e FROM Empleado e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
            "LOWER(e.departamento.nombre_dept) LIKE LOWER(CONCAT('%', :nombreDept, '%')) AND " +
            "e.salarioAnual >= :salario")
    List<Empleado> buscarPorParametros(@Param("nombre") String nombre,
                                       @Param("nombreDept") String nombreDept,
                                       @Param("salario") Long salario);
}
