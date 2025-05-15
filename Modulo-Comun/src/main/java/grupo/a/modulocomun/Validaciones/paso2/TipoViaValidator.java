package grupo.a.modulocomun.Validaciones.paso2;

import grupo.a.modulocomun.Servicios.RepositoryManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoViaValidator implements ConstraintValidator<TipoViaValidation, Long> {

    @Autowired
    private RepositoryManager repositoryManager;

    @Override
    public boolean isValid(Long tipoViaId, ConstraintValidatorContext context) {
        return repositoryManager.getTipoViaRepository().existsById(tipoViaId);
    }
}
