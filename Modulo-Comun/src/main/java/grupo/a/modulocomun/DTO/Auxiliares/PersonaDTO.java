package grupo.a.modulocomun.DTO.Auxiliares;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {

    private String nombre;
    private String apellido;

    private Long genero;

    private int edad;
    private String fechaNacimiento;

    private int prefijoTel;
    private String telefono;
    private String otroTelefono;
    private String email;

    private DireccionDTO direccion;

    private Long paisNacimiento;

    private String tipoDocumento;
    private String documento;
}
