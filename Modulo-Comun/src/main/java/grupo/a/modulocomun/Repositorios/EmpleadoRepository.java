package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
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
    /*
    @EntityGraph(attributePaths = {"departamento"})
    @Query("SELECT e FROM Empleado e WHERE " +
            "(COALESCE(:nombre, '') = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(COALESCE(:departamento, '') = '' OR e.departamento IS NULL OR LOWER(e.departamento.nombre_dept) LIKE LOWER(CONCAT('%', :departamento, '%'))) AND " +
            "(:salarioMin = 0 OR e.salarioAnual >= :salarioMin)")
    List<Empleado> buscarFiltrados(
            @Param("nombre") String nombre,
            @Param("departamento") String departamento,
            @Param("salarioMin") BigDecimal salarioMin);

     */

    Optional<Empleado> findByEmail(String email);
    /*
    // Método con @Query
    @EntityGraph(attributePaths = {"departamento"})
    @Query("SELECT e FROM Empleado e WHERE " +
            "(COALESCE(:nombre, '') = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(COALESCE(:nombresDepartamentos) IS NULL OR e.departamento IS NULL OR LOWER(e.departamento.nombre_dept) IN :nombresDepartamentos) AND " +
            "(:salarioMin = 0 OR e.salarioAnual >= :salarioMin) AND " +
            "(:salarioMax = 0 OR e.salarioAnual <= :salarioMax)")
    List<Empleado> buscarFiltradosAvanzado(
            @Param("nombre") String nombre,
            @Param("nombresDepartamentos") List<String> nombresDepartamentos,
            @Param("salarioMin") BigDecimal salarioMin,
            @Param("salarioMax") BigDecimal salarioMax);

     */

    // Versión CORRECTA del método query

    List<Empleado> findByNombreContainingIgnoreCaseAndDepartamentoNombredeptInAndSalarioAnualBetween(
            String nombre,
            List<String> nombreDept,
            BigDecimal salarioMin,
            BigDecimal salarioMax);
    // Búsqueda solo por nombre (ignorando mayúsculas/minúsculas)
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);

    // Búsqueda por nombre y departamento
    List<Empleado> findByNombreContainingIgnoreCaseAndDepartamentoNombredeptIn(
            String nombre, List<String> departamentos);

    // Búsqueda por nombre y salario
    List<Empleado> findByNombreContainingIgnoreCaseAndSalarioAnualBetween(
            String nombre, BigDecimal salarioMin, BigDecimal salarioMax);

    // Búsqueda por departamento y salario
    List<Empleado> findByDepartamentoNombredeptInAndSalarioAnualBetween(
            List<String> departamentos, BigDecimal salarioMin, BigDecimal salarioMax);

    // Búsqueda solo por departamento
    List<Empleado> findByDepartamentoNombredeptIn(List<String> departamentos);

    // Búsqueda solo por salario
    List<Empleado> findBySalarioAnualBetween(BigDecimal salarioMin, BigDecimal salarioMax);

}
