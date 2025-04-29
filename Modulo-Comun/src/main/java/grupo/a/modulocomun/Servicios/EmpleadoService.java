package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Direccion;
import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Entidades.DatosBancarios;
import grupo.a.modulocomun.Entidades.Departamento;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Maestros.*;
import grupo.a.modulocomun.Entidades.Usuario;
import grupo.a.modulocomun.Repositorios.EmpleadoRepository;
import grupo.a.modulocomun.Repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private RepositoryManager repositoryManager;

    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cargarEmpleado(){

        empleadoRepository.save(insertarEmpleado1());
        empleadoRepository.save(insertarEmpleado2());
        empleadoRepository.save(insertarEmpleado3());
        empleadoRepository.save(insertarEmpleado4());

    }

    public Empleado insertarEmpleado1(){
        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Laura");
        empleado1.setApellido("Martínez");

        empleado1.setGenero(repositoryManager.getGeneroRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado1.setEdad(28);
        empleado1.setFecha_nacimiento("1996-07-23");
        empleado1.setPrefijoTel(34);
        empleado1.setTelefono("612345678");
        empleado1.setOtroTelefono("699112233");
        empleado1.setEmail("laura.martinez@empresa.com");

        empleado1.setDireccion(new Direccion(repositoryManager.getTipoViaRepository().findById(2L)
                .orElseThrow(EntityNotFoundException::new),
                "Avenida del Sol", 10, 2, 1, "A", "Sevilla", "41001", "Andalucía"));

        empleado1.setPaisNacimiento(repositoryManager.getPaisRepository().findById(2L).orElseThrow(EntityNotFoundException::new));
        empleado1.setTipoDocumento(repositoryManager.getTipoDocumentoRepository().findById(2L).orElseThrow(EntityNotFoundException::new));
        empleado1.setDocumento("98765432Z");

        empleado1.setAdministrador(false);
        empleado1.setComentarios("Incorporada recientemente al departamento de ventas.");
        empleado1.setSalarioAnual(28000L);
        empleado1.setComisionAnual(1500L);
        empleado1.setFecha_alta(LocalDate.of(2023, 6, 1));

        empleado1.setEspecialidades(List.of(
                repositoryManager.getEspecialidadesRepository().findById(2L).orElseThrow(EntityNotFoundException::new)
        ));

        return empleado1;
    }

    public Empleado insertarEmpleado2(){
        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Ana");
        empleado2.setApellido("García");

        empleado2.setGenero(repositoryManager.getGeneroRepository().findById(2L).orElseThrow(EntityNotFoundException::new));
        empleado2.setEdad(30);
        empleado2.setFecha_nacimiento("1995-04-12");
        empleado2.setPrefijoTel(34);
        empleado2.setTelefono("600123456");
        empleado2.setOtroTelefono("699987654");
        empleado2.setEmail("ana.garcia@empresa.com");

        empleado2.setDireccion(new Direccion(repositoryManager.getTipoViaRepository().findById(1L)
                .orElseThrow(EntityNotFoundException::new),
                "Calle Mayor",24, 12, 3, "B", "Madrid", "28013", "Madrid"));

        empleado2.setPaisNacimiento(repositoryManager.getPaisRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado2.setTipoDocumento(repositoryManager.getTipoDocumentoRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado2.setDocumento("12345678A");

        empleado2.setAdministrador(true);
        empleado2.setComentarios("Trabajadora muy eficiente.");
        empleado2.setSalarioAnual(35000L);
        empleado2.setComisionAnual(3000L);
        empleado2.setFecha_alta(LocalDate.of(2022, 1, 10));


        empleado2.setEspecialidades(List.of(
                repositoryManager.getEspecialidadesRepository().findById(1L).orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository().findById(4L).orElseThrow(EntityNotFoundException::new)
        ));

        return empleado2;
    }

    public Empleado insertarEmpleado3(){
        Empleado empleado3 = new Empleado();
        empleado3.setNombre("Carlos");
        empleado3.setApellido("López");

        empleado3.setGenero(repositoryManager.getGeneroRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado3.setEdad(40);
        empleado3.setFecha_nacimiento("1984-02-15");
        empleado3.setPrefijoTel(34);
        empleado3.setTelefono("622334455");
        empleado3.setOtroTelefono("688554433");
        empleado3.setEmail("carlos.lopez@empresa.com");

        empleado3.setDireccion(new Direccion(repositoryManager.getTipoViaRepository().findById(3L)
                .orElseThrow(EntityNotFoundException::new),
                "Camino Real", 45, 0, 0, "", "Valencia", "46001", "Comunidad Valenciana"));

        empleado3.setPaisNacimiento(repositoryManager.getPaisRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado3.setTipoDocumento(repositoryManager.getTipoDocumentoRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado3.setDocumento("11223344B");

        empleado3.setAdministrador(true);
        empleado3.setComentarios("Responsable del área de IT.");
        empleado3.setSalarioAnual(50000L);
        empleado3.setComisionAnual(5000L);
        empleado3.setFecha_alta(LocalDate.of(2021, 3, 15));

        empleado3.setEspecialidades(List.of(
                repositoryManager.getEspecialidadesRepository().findById(3L).orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository().findById(5L).orElseThrow(EntityNotFoundException::new)
        ));

        return empleado3;
    }

    public Empleado insertarEmpleado4(){
        Empleado empleado4 = new Empleado();
        empleado4.setNombre("Elena");
        empleado4.setApellido("Ríos");

        empleado4.setGenero(repositoryManager.getGeneroRepository().findById(2L).orElseThrow(EntityNotFoundException::new));
        empleado4.setEdad(35);
        empleado4.setFecha_nacimiento("1989-09-10");
        empleado4.setPrefijoTel(34);
        empleado4.setTelefono("633445566");
        empleado4.setOtroTelefono("699776655");
        empleado4.setEmail("elena.rios@empresa.com");

        empleado4.setDireccion(new Direccion(repositoryManager.getTipoViaRepository().findById(1L)
                .orElseThrow(EntityNotFoundException::new),
                "Calle Luna", 5, 1, 1, "C", "Barcelona", "08001", "Cataluña"));

        empleado4.setPaisNacimiento(repositoryManager.getPaisRepository().findById(3L).orElseThrow(EntityNotFoundException::new));
        empleado4.setTipoDocumento(repositoryManager.getTipoDocumentoRepository().findById(1L).orElseThrow(EntityNotFoundException::new));
        empleado4.setDocumento("44332211C");

        empleado4.setAdministrador(false);
        empleado4.setComentarios("Experta en atención al cliente y gestión de incidencias.");
        empleado4.setSalarioAnual(32000L);
        empleado4.setComisionAnual(2500L);
        empleado4.setFecha_alta(LocalDate.of(2020, 11, 20));

        empleado4.setEspecialidades(List.of(
                repositoryManager.getEspecialidadesRepository().findById(2L).orElseThrow(EntityNotFoundException::new),
                repositoryManager.getEspecialidadesRepository().findById(5L).orElseThrow(EntityNotFoundException::new)
        ));

        return empleado4;
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id){
        return empleadoRepository.findById(id);
    }

    public void guardarEmpleado(EmpleadoDTO empleadoDTO, String emailUsuario) {
        // Obtener el usuario actual por email
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailUsuario);
        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con email: " + emailUsuario);
        }

        Usuario usuario = usuarioOptional.get();

        // Crear y configurar el empleado
        Empleado empleado = new Empleado();

        // Asignar campos básicos
        asignarCamposSimples(empleado, empleadoDTO);

        // Asignar relaciones
        asignarGenero(empleado, empleadoDTO);
        asignarDireccion(empleado, empleadoDTO);
        asignarPaisNacimiento(empleado, empleadoDTO);
        asignarDatosBancarios(empleado, empleadoDTO);
        asignarEspecialidades(empleado, empleadoDTO);
        asignarDepartamento(empleado, empleadoDTO);
        asignarTipoDocumento(empleado, empleadoDTO);

        // Relacionar con el usuario
        empleado.setUsuario(usuario);

        // Guardar el empleado
        empleadoRepository.save(empleado);

        // Actualizar la relación inversa (opcional)
        usuario.setEmpleado(empleado);
        usuarioRepository.save(usuario);
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
