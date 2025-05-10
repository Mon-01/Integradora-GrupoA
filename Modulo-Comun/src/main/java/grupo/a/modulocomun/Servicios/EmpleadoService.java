package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.UsuarioDTO;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    /*
    * añadir caducidad en insertar empleado para que se guarde en la bbdd
    * */

    @Autowired
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final DepartamentoService departamentoService;

    @Autowired
    private RepositoryManager repositoryManager;

    public EmpleadoService(EmpleadoRepository empleadoRepository, UsuarioRepository usuarioRepository, DepartamentoService departamentoService) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.departamentoService = departamentoService;
    }

    public void validarMaestrosPaso1(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
        if (empleadoDTO.getGenero() == null) {
            bindingResult.rejectValue("genero", "validation.notNull");
        } else if (!repositoryManager.getGeneroRepository().existsById(empleadoDTO.getGenero())) {
            bindingResult.rejectValue("genero", "valor.invalido");
        }

        if (!repositoryManager.getPaisRepository().existsById(empleadoDTO.getPaisNacimiento())) {
            bindingResult.rejectValue("paisNacimiento", "valor.invalido");
        }
    }

    public void validarMaestrosPaso2(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
        if (empleadoDTO.getTipoDocumento() == null) {
            bindingResult.rejectValue("tipoDocumento", "validation.notNull");
        } else if (!repositoryManager.getTipoDocumentoRepository().existsById(empleadoDTO.getTipoDocumento())) {
            bindingResult.rejectValue("tipoDocumento", "valor.invalido");
        }

        if (!repositoryManager.getTipoViaRepository().existsById(empleadoDTO.getDireccion().getTipoVia())) {
            bindingResult.rejectValue("direccion.tipoVia", "valor.invalido");
        }
    }

    public void validarMaestrosPaso3(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
            if (!repositoryManager.getDepartamentoRepository().existsById(empleadoDTO.getIdDepartamento())) {
            bindingResult.rejectValue("idDepartamento", "valor.invalido");
        }

        if (empleadoDTO.getEspecializaciones() == null || empleadoDTO.getEspecializaciones().isEmpty()) {
            bindingResult.rejectValue("especializaciones", "validation.notNull");
        } else if (
                //usamos un stream para que devuelva el primer error de id que encuentre
                empleadoDTO.getEspecializaciones().stream()
            .anyMatch(id -> !repositoryManager.getEspecialidadesRepository().existsById(id))
        ){
            bindingResult.rejectValue("especializaciones", "valor.invalido");
        }
    }

    public void validarMaestrosPaso4(EmpleadoDTO empleadoDTO, BindingResult bindingResult) {
            if (!repositoryManager.getEntidadBancariaRepository().existsById(empleadoDTO.getDatosBancarios().getEntidadBancaria())) {
            bindingResult.rejectValue("datosBancarios.entidadBancaria", "valor.invalido");
        }

        if (!repositoryManager.getTipoTarjetaRepository().existsById(empleadoDTO.getDatosBancarios().getTarjeta().getTipo())) {
            bindingResult.rejectValue("datosBancarios.tarjeta.tipo", "valor.invalido");
        }
    }

    @Transactional
    public void cargarEmpleado() {
        // Primero asegurarnos que los departamentos existen
        departamentoService.cargarDepartamentos();

        // Crear empleados asignando departamentos específicos
        Empleado emp1 = insertarEmpleado1();
        emp1.setDepartamento(repositoryManager.getDepartamentoRepository().findDepartamentoByNombre("Administración")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Administración no encontrado")));

        Empleado emp2 = insertarEmpleado2();
        emp2.setDepartamento(repositoryManager.getDepartamentoRepository().findDepartamentoByNombre("Tecnología")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Tecnología no encontrado")));

        Empleado emp3 = insertarEmpleado3();
        emp3.setDepartamento(repositoryManager.getDepartamentoRepository().findDepartamentoByNombre("Tecnología")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Tecnología no encontrado")));

        Empleado emp4 = insertarEmpleado4();
        emp4.setDepartamento(repositoryManager.getDepartamentoRepository().findDepartamentoByNombre("Recursos Humanos")
                .orElseThrow(() -> new EntityNotFoundException("Departamento Recursos Humanos no encontrado")));

        // Guardar todos los empleados
        empleadoRepository.saveAll(List.of(emp1, emp2, emp3, emp4));
    }

    //insertar datos empleados

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
        empleado1.setSalarioAnual(new BigDecimal("28000.00"));
        empleado1.setComisionAnual(new BigDecimal("1500.00"));
        empleado1.setFecha_alta(LocalDate.of(2023, 6, 1));

        empleado1.setEspecializaciones(List.of(
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
        empleado2.setSalarioAnual(new BigDecimal("35000.00"));
        empleado2.setComisionAnual(new BigDecimal("15000.90"));
        empleado2.setFecha_alta(LocalDate.of(2022, 1, 10));


        empleado2.setEspecializaciones(List.of(
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
        empleado3.setSalarioAnual(new BigDecimal("5000.10"));
        empleado3.setComisionAnual(new BigDecimal("15000.00"));
        empleado3.setFecha_alta(LocalDate.of(2021, 3, 15));

        empleado3.setEspecializaciones(List.of(
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
        empleado4.setSalarioAnual(new BigDecimal("35000.00"));
        empleado4.setComisionAnual(new BigDecimal("5000.00"));
        empleado4.setFecha_alta(LocalDate.of(2020, 11, 20));

        empleado4.setEspecializaciones(List.of(
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

        // Asignar campos básicos(nombre,teléfono...)
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

    //asignaciones para guardar el empleado en la bbdd
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
        empleado.setSalarioAnual(new BigDecimal(empleadoDTO.getSalarioAnual()));
        if(empleadoDTO.getComisionAnual().isEmpty()){
            empleado.setComisionAnual(BigDecimal.ZERO);
        }else{
           empleado.setComisionAnual(new BigDecimal(empleadoDTO.getComisionAnual()));
        }
        empleado.setImagenBase64(empleadoDTO.getImagenBase64());
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
        //Caducidad caducidad =
        //tarjetaCredito.setCaducidad(empleadoDTO.getDatosBancarios().getTarjeta().getCaducidad());

        datosBancarios.setTarjeta(tarjetaCredito);
        datosBancarios.setNumCuenta(empleadoDTO.getDatosBancarios().getNumCuenta());
        EntidadBancaria entidadBancaria = repositoryManager.getEntidadBancariaRepository()
                        .findById(empleadoDTO.getDatosBancarios().getEntidadBancaria())
                                .orElseThrow(() -> new EntityNotFoundException("Entidad bancaria no encontrado"));
        datosBancarios.setEntidadBancaria(entidadBancaria);
        empleado.setDatosBancarios(datosBancarios);
    }

    public void asignarEspecialidades(Empleado empleado, EmpleadoDTO empleadoDTO) {
        List<Especialidades> especializaciones = empleadoDTO.getEspecializaciones()
                .stream()
                .map(id -> repositoryManager.getEspecialidadesRepository().findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Especialidad no encontrada")))
                .collect(Collectors.toList());

        empleado.setEspecializaciones(especializaciones);
    }

    public void asignarDepartamento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        Departamento departamento = repositoryManager.getDepartamentoRepository()
                .findById(empleadoDTO.getIdDepartamento())
                .orElseThrow(() -> new EntityNotFoundException("Departamento no encontrado"));
        empleado.setDepartamento(departamento);
    }

    public void asignarTipoDocumento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        TipoDocumento tipoDocumento = repositoryManager.getTipoDocumentoRepository()
                .findById(empleadoDTO.getTipoDocumento())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
        empleado.setTipoDocumento(tipoDocumento);
    }
    public List<Empleado> obtenerTodosEmpleados() {
        return empleadoRepository.findAll();
    }

    public EmpleadoDTO convertirEmpleadoADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId_empleado(empleado.getId_empleado());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setTelefono(empleado.getPrefijoTel() + " " + empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFecha_nacimiento());
        dto.setSalarioAnual(empleado.getSalarioAnual().toString());
        dto.setComisionAnual(empleado.getComisionAnual().toString());
        dto.setComentarios(empleado.getComentarios());

        // Datos del departamento
        if(empleado.getDepartamento() != null) {
            dto.setIdDepartamento(empleado.getDepartamento().getId_dept());
            dto.setDepartamento(departamentoService.convertirDTO(empleado.getDepartamento()));
        }

        // Datos del usuario si existe
        if(empleado.getUsuario() != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId_usuario(empleado.getUsuario().getId_usuario());
            usuarioDTO.setEmail(empleado.getUsuario().getEmail());
            usuarioDTO.setBloqueado(empleado.getUsuario().isBloqueado());
            usuarioDTO.setMotivoBloqueo(empleado.getUsuario().getMotivoBloqueo());
            dto.setUsuario(usuarioDTO);
        }

        return dto;
    }

    public Optional<Empleado> obtenerEmpleadoPorCorreo(String correo) {
        return empleadoRepository.findByEmail(correo);
    }
    public String obtenerImagenBase64PorCorreo(String correo) {
        Optional<Empleado> empleadoOpt = obtenerEmpleadoPorCorreo(correo);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            if (empleado.getImagenBase64() != null && !empleado.getImagenBase64().isEmpty()) {
                return empleado.getImagenBase64(); // Devuelve la imagen en base64
            }
        }
        return null; // Devuelve null si no se encontró imagen
    }


}
