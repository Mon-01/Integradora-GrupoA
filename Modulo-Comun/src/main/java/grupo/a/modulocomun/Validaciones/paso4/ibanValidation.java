package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ibanValidator.class)
@Documented
public @interface ibanValidation {
    String message() default "{error.desconocido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
