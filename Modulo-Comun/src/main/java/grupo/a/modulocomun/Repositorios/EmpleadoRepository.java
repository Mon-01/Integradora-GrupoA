package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Empleado;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    /*
    List <Empleado> findByNombreContainingIgnoreCaseAndDepartamento_Nombre_deptAndAndSalarioAnualGreaterThanEqual(String nombre, String nombreDept, Long salarioMinimo)

    ;

     */
  /*  @Query("SELECT e FROM Empleado e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
            "LOWER(e.departamento.nombre_dept) LIKE LOWER(CONCAT('%', :nombreDept, '%')) AND " +
            "e.salarioAnual >= :salario")
    List<Empleado> buscarPorParametros(@Param("nombre") String nombre,
                                       @Param("nombreDept") String nombreDept,
                                       @Param("salario") Long salario);

   */
    @EntityGraph(attributePaths = {"departamento"})
    @Query("SELECT e FROM Empleado e WHERE " +
            "(COALESCE(:nombre, '') = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(COALESCE(:departamento, '') = '' OR e.departamento IS NULL OR LOWER(e.departamento.nombre_dept) LIKE LOWER(CONCAT('%', :departamento, '%'))) AND " +
            "(:salarioMin = 0 OR e.salarioAnual >= :salarioMin)")
    List<Empleado> buscarFiltrados(
            @Param("nombre") String nombre,
            @Param("departamento") String departamento,
            @Param("salarioMin") BigDecimal salarioMin);
}
