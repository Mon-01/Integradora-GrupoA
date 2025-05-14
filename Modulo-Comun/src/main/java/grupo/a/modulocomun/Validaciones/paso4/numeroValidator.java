package grupo.a.modulocomun.Validaciones.paso4;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class numeroValidator implements ConstraintValidator<numeroValidation, String> {
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        if (valor == null || valor.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{validation.notNull}")
                    .addConstraintViolation();
            return false;
        }

        valor = valor.replaceAll("\\s+", ""); // elimina todos los espacios

        if (!valor.matches("\\d{16}")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{numeroTarjeta.formato}")
                    .addConstraintViolation();
            return false;
        }

        if (!pasaAlgoritmoLuhn(valor)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{numeroTarjeta.algoritmo}")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    //comprobamos que pasa el algoritmo
    private boolean pasaAlgoritmoLuhn(String numero) {
        int suma = 0;                  // Acumula la suma total de los dígitos procesados
        boolean alternar = false;     // Indica si se debe duplicar el dígito actual

        // Recorre el número de derecha a izquierda
        for (int i = numero.length() - 1; i >= 0; i--) {
            // Convierte el carácter actual en número entero
            int digito = Character.getNumericValue(numero.charAt(i));

            if (alternar) {
                digito *= 2;

                // equivale a sumar los dos dígitos del número
                if (digito > 9) {
                    digito -= 9;
                }
            }

            suma += digito;

            // Cambia el estado de alternar para el siguiente dígito
            alternar = !alternar;
        }

        // Si la suma total es múltiplo de 10, el número es válido
        return suma % 10 == 0;
    }
}
