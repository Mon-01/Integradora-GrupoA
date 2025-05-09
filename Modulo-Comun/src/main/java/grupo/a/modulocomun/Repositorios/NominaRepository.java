package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Nomina;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Long> {
    /*
    // Versión CORRECTA usando el nombre exacto del campo
    List<Nomina> findByEmpleado_Id_Empleado(Long idEmpleado);

     */

@Query("SELECT DISTINCT n FROM Nomina n " +
           "LEFT JOIN FETCH n.lineas " +
           "WHERE n.empleado.id_empleado = :empleadoId")
    List<Nomina> findNominasConLineasByEmpleadoId(@Param("empleadoId") Long empleadoId);

    // Otra alternativa válida
 //   List<Nomina> findByEmpleadoIdEmpleado(Long idEmpleado);

/*
    @EntityGraph(attributePaths = {"empleado"})
    @Query("SELECT n FROM Nomina n " +
            "JOIN n.empleado e " +
            "WHERE (COALESCE(:nombre, '') = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Nomina> filtroNomina(@Param("nombre") String nombre);

 */
@EntityGraph(attributePaths = {"empleado"})
@Query("SELECT DISTINCT n FROM Nomina n " +
        "JOIN n.empleado e " +
        "WHERE (COALESCE(:nombre, '') = '' OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
        "AND (:fecha IS NULL OR n.fecha = :fecha)")
List<Nomina> filtroNomina(@Param("nombre") String nombre, @Param("fecha") LocalDate fecha);

}
