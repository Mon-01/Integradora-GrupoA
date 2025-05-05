package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

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


}
