package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class comisionValidator implements ConstraintValidator<comisionValidation, String> {
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        if (valor == null || valor.isBlank()) {

            // Si el valor es null, lo consideramos válido, ya que es opcional
            return true;
        }

        BigDecimal value;
        try{
            //para que no salte nullPointerException
            value = new BigDecimal(valor);
        }catch (NumberFormatException e){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false;
        }

        if(value.compareTo(BigDecimal.ZERO) <= 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{salario.positivo}")
                    .addConstraintViolation();
            return false;
        }

        //resta los número totales menos los decimales para sacar los enteros
        int integerDigits = value.precision() - value.scale();

        if (integerDigits > 8 || value.scale() > 2) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{salario.formato}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
