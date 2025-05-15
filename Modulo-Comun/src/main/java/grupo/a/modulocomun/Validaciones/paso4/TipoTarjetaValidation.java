package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = TipoTarjetaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoTarjetaValidation {
    String message() default "Tipo de tarjeta inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

