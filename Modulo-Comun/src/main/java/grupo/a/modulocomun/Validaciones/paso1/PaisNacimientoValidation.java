package grupo.a.modulocomun.Validaciones.paso1;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaisNacimientoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaisNacimientoValidation {
    String message() default "El país de nacimiento es inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
