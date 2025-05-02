package grupo.a.modulocomun.Validaciones.paso1;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Constraint(validatedBy = FechaNacValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaNacValidation {
    String message() default "{error.desconocido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}