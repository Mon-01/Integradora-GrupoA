package grupo.a.modulocomun.Validaciones.paso1;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GeneroValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneroValidation {
    String message() default "El valor no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
