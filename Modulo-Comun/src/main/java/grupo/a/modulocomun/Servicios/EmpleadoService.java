package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Direccion;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Maestros.Genero;
import grupo.a.modulocomun.Entidades.Maestros.TipoVia;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private RepositoryManager repositoryManager;

    public void guardarEmpleado(Empleado empleado){
        empleadoRepository.save(empleado);
    }

    public Empleado convertirDTOaEntidad(EmpleadoDTO empleadoDTO) {
        Empleado empleado = new Empleado();

        // Asignar campos simples
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEdad(empleadoDTO.getEdad());
        empleado.setFecha_nacimiento(empleadoDTO.getFecha_nacimiento());
        empleado.setDocumento(empleadoDTO.getDocumento());
        empleado.setPrefijoTel(empleadoDTO.getPrefijoTel());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setOtroTelefono(empleadoDTO.getOtroTelefono());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setComentarios(empleadoDTO.getComentarios());
        empleado.setSalarioAnual(empleadoDTO.getSalarioAnual());
        empleado.setComisionAnual(empleadoDTO.getComisionAnual());

        Genero genero = repositoryManager.getGeneroRepository()
                .findById(empleadoDTO.getGenero())
                .orElseThrow(() -> new RuntimeException("Genero no encontrado"));
        empleado.setGenero(genero);

        Direccion direccion = new Direccion();
        TipoVia tipoVia = repositoryManager.getTipoViaRepository()
                        .findById(empleadoDTO.getDireccion().getTipoVia())
                                .orElseThrow(() -> new EntityNotFoundException())
        direccion.setTipoVia();
        direccion.setNombreVia(empleadoDTO.getDireccion().getNombreVia());
        direccion.setNumero(empleadoDTO.getDireccion().getNumero());
        direccion.setPortal(empleadoDTO.getDireccion().getPortal());
        direccion.setPlanta(empleadoDTO.getDireccion().getPlanta());
        direccion.setPuerta(empleadoDTO.getDireccion().getPuerta());
        direccion.setLocalidad(empleadoDTO.getDireccion().getLocalidad());
        direccion.setRegion(empleadoDTO.getDireccion().getRegion());
        direccion.setCod_postal(empleadoDTO.getDireccion().getCod_postal());

        empleado.setDireccion(direccion);

        // Asignar el país de nacimiento (Pais)
        Pais paisNacimiento = paisRepository.findById(empleadoDTO.getPaisNacimientoId())
                .orElseThrow(() -> new RuntimeException("Pais de nacimiento no encontrado"));
        empleado.setPaisNacimiento(paisNacimiento);

        // Asignar tarjeta de crédito
        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        tarjetaCredito.setTipo(tipoTarjetaRepository.findById(empleadoDTO.getTipoTarjetaId())
                .orElseThrow(() -> new RuntimeException("Tipo de tarjeta no encontrado")));
        tarjetaCredito.setNumero(empleadoDTO.getNumero());
        tarjetaCredito.setCvc(empleadoDTO.getCvc());
        empleado.setTarjeta(tarjetaCredito);

        // Asignar especializaciones
        List<Especialidades> especializaciones = new ArrayList<>();
        for (Long especialidadId : empleadoDTO.getEspecializaciones()) {
            Especialidades especialidad = especialidadesRepository.findById(especialidadId)
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            especializaciones.add(especialidad);
        }
        empleado.setEspecializaciones(especializaciones);

        // Asignar departamento
        Departamento departamento = departamentoRepository.findById(empleadoDTO.getDepartamentoId())
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        empleado.setDepartamento(departamento);

        // Asignar tipo de documento
        empleado.setTipoDocumento(empleadoDTO.getTipoDocumento());
        empleado.setDocumento(empleadoDTO.getDocumento());

        return empleado;
    }


}
