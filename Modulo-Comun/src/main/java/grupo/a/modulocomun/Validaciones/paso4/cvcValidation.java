package grupo.a.modulocomun.Validaciones.paso4;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = cvcValidator.class)
@Documented
public @interface cvcValidation {
    String message() default "{error.desconocido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
