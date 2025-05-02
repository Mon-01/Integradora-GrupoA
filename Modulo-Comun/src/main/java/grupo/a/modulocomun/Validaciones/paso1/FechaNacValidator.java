package grupo.a.modulocomun.Validaciones.paso1;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FechaNacValidator  implements ConstraintValidator<FechaNacValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false; // No puede estar vacío ni ser nula
        }

        DateTimeFormatter[] formatosValidos = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd/M/yyyy"),
                DateTimeFormatter.ofPattern("d/MM/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy")
        };

        //comprueba si la fecha cumple alguno de los formatos válidos y devuelve el primero
        Optional<LocalDate> fechaValida = Stream.of(formatosValidos)
                .map(formatter -> {
                    try {
                        return LocalDate.parse(value, formatter);
                    } catch (DateTimeParseException e) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate("{fechaNacimiento.nula}")
                                .addConstraintViolation();
                        return null;                    }
                })
                .filter(Objects::nonNull)   //por si todos fallan
                .findFirst();

        //comprobamos que la fecha sea superior a 18 años
        int edad = Period.between(fechaValida.get(), LocalDate.now()).getYears();
        if (edad < 18) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{fechaNacimiento.menor}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}