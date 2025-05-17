package grupo.a.modulocomun.Validaciones.paso1;

import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneroValidator implements ConstraintValidator<GeneroValidation, Long> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (value == null) {
            context.buildConstraintViolationWithTemplate("El género es obligatorio")
                    .addConstraintViolation();
            valido = false;
        } else if (!repositoryManager.getGeneroRepository().existsById(value)) {

            valido = false;
        }
        return valido;
    }
}
