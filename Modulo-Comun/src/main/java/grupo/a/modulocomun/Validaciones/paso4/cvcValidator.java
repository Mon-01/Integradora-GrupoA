package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class cvcValidator implements ConstraintValidator<cvcValidation,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

//poner notBlank igual para que sea obligatorio
        if (value == null || value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false;
        }

        if (!value.matches("\\d{3,4}")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{cvc.formato}")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
