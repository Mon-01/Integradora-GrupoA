package grupo.a.modulocomun.Validaciones.paso1;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = edadValidator2.class)
@Documented
public @interface edadValidation2 {
    String message() default "{error.desconocido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}