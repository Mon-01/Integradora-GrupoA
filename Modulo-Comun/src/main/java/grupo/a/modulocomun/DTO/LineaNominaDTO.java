package grupo.a.modulocomun.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaNominaDTO {
    private Long id;

    @NotBlank(message = "El concepto no puede estar vacío")
    @Size(max = 100, message = "El concepto no puede exceder los 100 caracteres")
    private String concepto;

    @Pattern(regexp = "^\\d{1,3}(\\.\\d{1,2})?$", message = "El porcentaje debe ser un número entre 0 y 100 con máximo 2 decimales")
    @DecimalMin(value = "0.0", message = "El porcentaje no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje no puede ser mayor a 100")
    private String porcentaje;

    @Pattern(regexp = "^\\d{1,10}(\\.\\d{1,2})?$", message = "El importe fijo debe ser un número con máximo 2 decimales")
    @DecimalMin(value = "0.0", message = "El importe fijo no puede ser negativo")
    private String importeFijo;

    @NotNull(message = "Debe especificar si es devengo o deducción")
    private Boolean esDevengo; // true para devengo (suma), false para deducción (resta)

    private String cantidad; // Este campo será calculado automáticamente
}