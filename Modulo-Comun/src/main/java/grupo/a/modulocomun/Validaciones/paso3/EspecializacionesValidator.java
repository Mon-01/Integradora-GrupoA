package grupo.a.modulocomun.Validaciones.paso3;

import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EspecializacionesValidator implements ConstraintValidator<EspecializacionesValidation, List<Long>> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(List<Long> especializaciones, ConstraintValidatorContext context) {
        if (especializaciones == null || especializaciones.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false;
        }

        //usamos un stream para que devuelva el primer error de id que encuentre
        return especializaciones.stream()
                .allMatch(id -> repositoryManager.getEspecialidadesRepository().existsById(id));
    }
}

