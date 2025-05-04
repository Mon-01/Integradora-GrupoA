package grupo.a.modulocomun.Validaciones.paso2;

import jakarta.validation.Payload;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = documentoValidator.class)
@Documented
public @interface ValidarDocumento {
    String message() default "Documento no válido para el tipo seleccionado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
