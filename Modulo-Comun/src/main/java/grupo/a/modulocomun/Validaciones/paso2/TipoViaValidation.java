package grupo.a.modulocomun.Validaciones.paso2;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TipoViaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoViaValidation {
    String message() default "Tipo de vía inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}