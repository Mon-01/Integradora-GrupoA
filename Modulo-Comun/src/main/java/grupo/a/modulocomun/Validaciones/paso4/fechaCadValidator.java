package grupo.a.modulocomun.Validaciones.paso4;

import grupo.a.modulocomun.Entidades.Auxiliares.Caducidad;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;

public class fechaCadValidator implements ConstraintValidator<fechaCadValidation, Caducidad> {

    @Override
    public boolean isValid(Caducidad caducidad, ConstraintValidatorContext context) {
        if (caducidad == null) {
            return true;
        }

        int mes = caducidad.getMes();
        int anio = caducidad.getAnio();

        if (mes < 1 || mes > 12) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mes inválido")
                    .addPropertyNode("mes")
                    .addConstraintViolation();
            return false;
        }

        if (anio < 2024) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Año inválido")
                    .addPropertyNode("anio")
                    .addConstraintViolation();
            return false;
        }

        // Construir la fecha ingresada y la actual
        YearMonth fechaIngresada = YearMonth.of(anio, mes);
        YearMonth fechaActual = YearMonth.now();

        if (fechaIngresada.isBefore(fechaActual)) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("La fecha no puede ser anterior al mes actual")
                    .addPropertyNode("mes")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

}
