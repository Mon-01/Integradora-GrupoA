package grupo.a.modulocomun.Validaciones.paso1;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Base64;

public class FotoValidator implements ConstraintValidator<FotoValidation, String> {

    private static final int MAX_SIZE_BYTES = 200 * 1024;

    @Override
    public boolean isValid(String base64, ConstraintValidatorContext context) {
        if (base64 == null || base64.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false;
        }

        try {
            // comprobación del formato mediante el encabezado en base64
            if (!base64.startsWith("data:image/jpeg;base64,") &&
                    !base64.startsWith("data:image/jpg;base64,") &&
                    !base64.startsWith("data:image/gif;base64,")) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("El formato debe ser jpg, jpeg o gif")
                        .addConstraintViolation();
                return false;
            }

            // Extraer la parte codificada (después de la coma)
            String base64Data = base64.substring(base64.indexOf(",") + 1);

            // Decodificar a bytes reales
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

            // Validar tamaño máximo
            if (decodedBytes.length > MAX_SIZE_BYTES) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("La imagen debe estar por debajo de los 200kb")
                        .addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
