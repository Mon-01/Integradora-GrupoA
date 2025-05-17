package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EntidadBancariaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EntidadBancariaValidation {
    String message() default "Entidad bancaria inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

