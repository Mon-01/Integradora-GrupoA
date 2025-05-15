package grupo.a.modulocomun.Validaciones.paso1;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FotoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FotoValidation {
    String message() default "La imagen debe ser JPG o GIF y no mayor de 200 KB";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
