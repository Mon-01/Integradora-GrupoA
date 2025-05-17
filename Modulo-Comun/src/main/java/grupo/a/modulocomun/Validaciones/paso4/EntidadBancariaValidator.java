package grupo.a.modulocomun.Validaciones.paso4;

import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntidadBancariaValidator implements ConstraintValidator<EntidadBancariaValidation, Long> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("La entidad bancaria es obligatorio")
                    .addConstraintViolation();
            valido = false;
        } else if (!repositoryManager.getEntidadBancariaRepository().existsById(value)) {

            valido = false;
        }
        return valido;
    }

}
