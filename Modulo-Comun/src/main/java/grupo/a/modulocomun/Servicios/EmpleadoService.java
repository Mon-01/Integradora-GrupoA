package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Direccion;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Entidades.DatosBancarios;
import grupo.a.modulocomun.Entidades.Departamento;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Maestros.*;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RepositoryManager repositoryManager;

    public void guardarEmpleado(EmpleadoDTO empleadoDTO){

        Empleado empleado = new Empleado();

        asignarCamposSimples(empleado,empleadoDTO);

        asignarGenero(empleado,empleadoDTO);

        asignarDireccion(empleado,empleadoDTO);

        asignarPaisNacimiento(empleado,empleadoDTO);

        asignarDatosBancarios(empleado,empleadoDTO);

        asignarEspecialidades(empleado,empleadoDTO);

        asignarDepartamento(empleado,empleadoDTO);

        asignarTipoDocumento(empleado,empleadoDTO);

        empleadoRepository.save(empleado);
    }


    private void asignarCamposSimples(Empleado empleado, EmpleadoDTO empleadoDTO) {

        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEdad(empleadoDTO.getEdad());
        empleado.setFecha_nacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setDocumento(empleadoDTO.getDocumento());
        empleado.setPrefijoTel(empleadoDTO.getPrefijoTel());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setOtroTelefono(empleadoDTO.getOtroTelefono());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setComentarios(empleadoDTO.getComentarios());
        empleado.setSalarioAnual(empleadoDTO.getSalarioAnual());
        empleado.setComisionAnual(empleadoDTO.getComisionAnual());
    }

    private void asignarGenero(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Genero genero = repositoryManager.getGeneroRepository()
                .findById(empleadoDTO.getGenero())
                .orElseThrow(() -> new EntityNotFoundException("Genero no encontrado"));
        empleado.setGenero(genero);
    }

    private void asignarDireccion(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Direccion direccion = new Direccion();
        TipoVia tipoVia = repositoryManager.getTipoViaRepository()
                .findById(empleadoDTO.getDireccion().getTipoVia())
                .orElseThrow(() -> new EntityNotFoundException("Tipo Via no encontrado"));
        direccion.setTipoVia(tipoVia);
        direccion.setNombreVia(empleadoDTO.getDireccion().getNombreVia());
        direccion.setNumero(empleadoDTO.getDireccion().getNumero());
        direccion.setPortal(empleadoDTO.getDireccion().getPortal());
        direccion.setPlanta(empleadoDTO.getDireccion().getPlanta());
        direccion.setPuerta(empleadoDTO.getDireccion().getPuerta());
        direccion.setLocalidad(empleadoDTO.getDireccion().getLocalidad());
        direccion.setRegion(empleadoDTO.getDireccion().getRegion());
        direccion.setCod_postal(empleadoDTO.getDireccion().getCod_postal());

        empleado.setDireccion(direccion);
    }

    public void asignarPaisNacimiento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Pais paisNacimiento = repositoryManager.getPaisRepository()
                .findById(empleadoDTO.getPaisNacimiento())
                .orElseThrow(() -> new EntityNotFoundException("Pais de nacimiento no encontrado"));
        empleado.setPaisNacimiento(paisNacimiento);
    }

    public void asignarDatosBancarios(Empleado empleado, EmpleadoDTO empleadoDTO) {
        DatosBancarios datosBancarios = new DatosBancarios();

        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        TipoTarjeta tipoTarjeta = repositoryManager.getTipoTarjetaRepository()
                .findById(empleadoDTO.getDatosBancarios().getTarjeta().getTipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo tarjeta no encontrado"));
        tarjetaCredito.setTipo(tipoTarjeta);
        tarjetaCredito.setNumero(empleadoDTO.getDatosBancarios().getTarjeta().getNumero());
        tarjetaCredito.setCvc(empleadoDTO.getDatosBancarios().getTarjeta().getCvc());

        datosBancarios.setTarjeta(tarjetaCredito);
        datosBancarios.setNumCuenta(empleadoDTO.getDatosBancarios().getNumCuenta());
        datosBancarios.setEntidadBancaria(empleadoDTO.getDatosBancarios().getEntidadBancaria());
        empleado.setDatosBancarios(datosBancarios);
    }

    public void asignarEspecialidades(Empleado empleado, EmpleadoDTO empleadoDTO) {
        List<Especialidades> especializaciones = empleadoDTO.getEspecializaciones()
                .stream()
                .map(id -> repositoryManager.getEspecialidadesRepository().findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Especialidad no encontrada")))
                .collect(Collectors.toList());

        empleado.setEspecialidades(especializaciones);
    }

    public void asignarDepartamento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Departamento departamento = repositoryManager.getDepartamentoRepository()
                .findById(empleadoDTO.getDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
        empleado.setDepartamento(departamento);
    }

    public void asignarTipoDocumento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        TipoDocumento tipoDocumento = repositoryManager.getTipoDocumentoRepository()
                .findById(empleadoDTO.getTipoDocumento())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
        empleado.setTipoDocumento(tipoDocumento);
    }
}
