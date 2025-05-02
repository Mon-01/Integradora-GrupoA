package grupo.a.modulocomun.Validaciones.paso4;

import grupo.a.modulocomun.Validaciones.paso1.edadValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = comisionValidator.class)
@Documented
public @interface comisionValidation {
    String message() default "{error.desconocido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}