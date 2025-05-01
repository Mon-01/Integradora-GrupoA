package grupo.a.modulocomun.Validaciones.paso1;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
//
//public class edadValidator implements ConstraintValidator<edadValidation, EmpleadoDTO> {
//
//    @Override
//    public boolean isValid(EmpleadoDTO empleadoDTO, ConstraintValidatorContext constraintValidatorContext) {
//
//        if (empleadoDTO.getFechaNacimiento() == null ) {
//            constraintValidatorContext.disableDefaultConstraintViolation();
//            constraintValidatorContext.
//                    buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
//                    .addPropertyNode("fechaNacimiento").addConstraintViolation();
//            return true;
//        }
//
//        return true;
//    }