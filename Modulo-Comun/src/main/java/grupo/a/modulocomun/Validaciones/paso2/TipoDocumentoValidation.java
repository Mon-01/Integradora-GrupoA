package grupo.a.modulocomun.Validaciones.paso2;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TipoDocumentoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoDocumentoValidation {
    String message() default "Tipo de documento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
