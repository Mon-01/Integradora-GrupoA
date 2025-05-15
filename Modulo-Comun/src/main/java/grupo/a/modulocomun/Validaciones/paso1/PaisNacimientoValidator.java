package grupo.a.modulocomun.Validaciones.paso1;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaisNacimientoValidator implements ConstraintValidator<PaisNacimientoValidation, Long> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (value == null ){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            valido = false;
            return valido;
        }else if(!repositoryManager.getPaisRepository().existsById(value)) {
            valido = false;
            return valido;
        }

        return valido;
    }
}
