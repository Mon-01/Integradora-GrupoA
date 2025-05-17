package grupo.a.modulocomun.Validaciones.paso3;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartamentoValidator.class)
@Documented
public @interface DepartamentoValidation {
    String message() default "Departamento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
