package grupo.a.modulocomun.Validaciones.paso3;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EspecializacionesValidator.class)
@Documented
public @interface EspecializacionesValidation {
    String message() default "Especializaciones inválidas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

