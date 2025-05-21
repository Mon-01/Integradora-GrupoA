package grupo.a.modulocomun.DTO;
import grupo.a.modulocomun.DTO.Auxiliares.PersonaDTO;
import grupo.a.modulocomun.Validaciones.paso1.FotoValidation;
import grupo.a.modulocomun.Validaciones.paso1.Paso1;
import grupo.a.modulocomun.Validaciones.paso3.DepartamentoValidation;
import grupo.a.modulocomun.Validaciones.paso3.EspecializacionesValidation;
import grupo.a.modulocomun.Validaciones.paso3.Paso3;
import grupo.a.modulocomun.Validaciones.paso4.Paso4;
import grupo.a.modulocomun.Validaciones.paso4.comisionValidation;
import grupo.a.modulocomun.Validaciones.paso4.salarioValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO extends PersonaDTO {
    private Long id_empleado;
    private String comentarios;

    @salarioValidation(groups = Paso4.class)
    private String salarioAnual;

    @comisionValidation(groups = Paso4.class)
    private String comisionAnual;

    @Valid
    private DatosBancariosDTO datosBancarios = new DatosBancariosDTO();

    @NotNull(message = "Este campo es obligatorio", groups = Paso3.class)
    @DepartamentoValidation(groups = Paso3.class)
    private Long idDepartamento = 1L;

    private DepartamentoDTO departamento;

    @Size(min = 2, message = "{valores.minimos}")
    @EspecializacionesValidation(groups = Paso3.class)
    private List<Long> especializaciones = new ArrayList<>();
    private UsuarioDTO usuario;
    private List<NominaDTO> nominas;

    @FotoValidation(groups = Paso1.class)
    private String imagen;

    private boolean activo;
    private LocalDate fechaBaja;

}