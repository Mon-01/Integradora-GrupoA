package grupo.a.modulocomun.Validaciones.paso3;

import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartamentoValidator implements ConstraintValidator<DepartamentoValidation, Long> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(Long departamentoId, ConstraintValidatorContext context) {
        return departamentoId == null || repositoryManager.getDepartamentoRepository().existsById(departamentoId);
    }
}

