package grupo.a.modulocomun.Validaciones.paso2;

import grupo.a.modulocomun.DTO.Auxiliares.PersonaDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class documentoValidator implements ConstraintValidator<ValidarDocumento, PersonaDTO> {

    @Override
    public void initialize(ValidarDocumento constraintAnnotation) {
    }

    @Override
    public boolean isValid(PersonaDTO persona, ConstraintValidatorContext context) {
        if (persona.getTipoDocumento() == null || persona.getDocumento() == null) {
            return true; // ya se validan por separado
        }

        Long tipo = persona.getTipoDocumento();
        String doc = persona.getDocumento();

        // Ejemplo de reglas:
        if (tipo == 1L) { // NIF
            return doc.matches("\\d{8}[A-Za-z]");
        } else if (tipo == 2L) { // NIE
            return doc.matches("[XYZ]\\d{7}[A-Za-z]");
        } else if (tipo == 3L) { // Pasaporte
            return doc.matches("[A-Z0-9]{5,15}");
        }

        return true; // por defecto válido si no hay reglas definidas
    }
}