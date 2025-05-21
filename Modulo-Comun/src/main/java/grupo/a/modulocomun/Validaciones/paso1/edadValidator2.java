package grupo.a.modulocomun.Validaciones.paso1;

import grupo.a.modulocomun.DTO.Auxiliares.PersonaDTO;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.DTO.filtros.PersonaEditarDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class edadValidator2 implements ConstraintValidator<edadValidation2, PersonaEditarDTO> {

    @Override
    public boolean isValid(PersonaEditarDTO value, ConstraintValidatorContext context) {

        if (value.getEdad() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addPropertyNode("edad")
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
                        return LocalDate.parse(value.getFechaNacimiento(), formatter);
                    } catch (DateTimeParseException e) {
                        return null;                    }
                })
                .filter(Objects::nonNull)   //por si todos fallan
                .findFirst();

        // Verificar si se encontró una fecha válida
        if (fechaValida.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{El Objeto fecha está vacío}")
                    .addConstraintViolation();
            return false;
        }

        if(Period.between(fechaValida.get(), LocalDate.now()).getYears() != value.getEdad()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{edad.valor}")
                    .addPropertyNode("edad")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }}