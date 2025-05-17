package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = fechaCadValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface fechaCadValidation {
    String message() default "La fecha no puede estar en el pasado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
