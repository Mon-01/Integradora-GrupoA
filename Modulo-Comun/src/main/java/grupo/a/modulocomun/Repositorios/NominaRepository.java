package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NominaRepository extends JpaRepository<Nomina, Long> {
    /*
    // Versión CORRECTA usando el nombre exacto del campo
    List<Nomina> findByEmpleado_Id_Empleado(Long idEmpleado);

    // Otra alternativa válida
    List<Nomina> findByEmpleadoIdEmpleado(Long idEmpleado);

     */
}
