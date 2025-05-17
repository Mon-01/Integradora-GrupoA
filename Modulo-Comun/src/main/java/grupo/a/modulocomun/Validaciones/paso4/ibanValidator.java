package grupo.a.modulocomun.Validaciones.paso4;

import grupo.a.modulocomun.DTO.DatosBancariosDTO;
import grupo.a.modulocomun.DTO.EmpleadoDTO;
import grupo.a.modulocomun.Entidades.Empleado;
import grupo.a.modulocomun.Entidades.Maestros.EntidadBancaria;
import grupo.a.modulocomun.Repositorios.Maestros.EntidadBancariaRepository;
import grupo.a.modulocomun.Servicios.Maestros.EntidadBancariaService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ibanValidator implements ConstraintValidator<ibanValidation, DatosBancariosDTO> {

    @Autowired
    EntidadBancariaService entidadBancariaService;

    @Autowired
    EntidadBancariaRepository entidadBancariaRepository;

    @Override
    public boolean isValid(DatosBancariosDTO value, ConstraintValidatorContext context) {

        Long entidadId = value.getEntidadBancaria();
        Optional<EntidadBancaria> entidadOpt = entidadBancariaRepository.findById(entidadId);

        if (entidadId == null || entidadOpt.isEmpty()) {
            return true; // No valida IBAN si entidad no está presente
        }

        String iban = value.getNumCuenta();

        if (iban == null || iban.isBlank()) {
            return buildViolation(context, "{validation.notNull}");
        }

        // Limpiar y normalizar IBAN (sin espacios y en mayúsculas)
        iban = iban.replaceAll("\\s+", "").toUpperCase();

        if (iban.length() != 24) {
            return buildViolation(context, "{iban.longitud}");
        }

        // Verificar estructura básica: dos letras, dos dígitos, veinte dígitos
        if (!iban.matches("^[A-Z]{2}\\d{22}$")) {
            return buildViolation(context, "{iban.formato}");
        }

        //cogemos los dos primeros (código del país del iban)
        if(!iban.substring(0,2)
                //lo comparamos con el código del país que obtenemos mediante el id
                .equals(entidadBancariaService.obtenerCodPaisPorId(value.getEntidadBancaria())))
        {
            return buildViolation(context, "{iban.codPais}");
        }

        if (!iban.substring(4, 8) // posiciones 5-8 (código entidad)
                .equals(entidadBancariaService.obtenerCodEntidadPorId(value.getEntidadBancaria())))
        {
            return buildViolation(context, "{iban.codEntidad}");
        }

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String mensaje) {
    //deshabilitamos el mensaeje por defecto
        context.disableDefaultConstraintViolation();
        //creamos una nueva restricción con un mensaje de error personalizado
        context.buildConstraintViolationWithTemplate(mensaje)
                //lo añadimos al campo donde queremos que se muestren los mensajes
                .addPropertyNode("numCuenta")
                //la añadimos para que el mensaje personalizado sea accesible durante la validación
                .addConstraintViolation();
        return false;
    }

     /*  validación del IBAN completo

        * public boolean esIBANValido(String iban) {
    // Reorganizar el IBAN como en el algoritmo del módulo 97
    String reformateado = iban.substring(4) + iban.substring(0, 4);

    // Convertir caracteres al formato numérico (A=10, B=11, ..., Z=35) usando Streams
    String convertido = reformateado.chars()  // Convertir el String a IntStream
        .mapToObj(c -> (char) c)  // Convertir int a Character
        .map(c -> {
            if (Character.isLetter(c)) {
                // Convertir letras A-Z a números 10-35
                return String.valueOf(c - 'A' + 10);
            } else {
                // Si es un dígito, dejarlo tal cual
                return String.valueOf(c);
            }
        })
        .collect(Collectors.joining()); // Unir los valores convertidos en un solo String

    try {
        // Convertir el String resultante en un número BigInteger y aplicar la validación del módulo 97
        BigInteger numero = new BigInteger(convertido);
        return numero.mod(BigInteger.valueOf(97)).intValue() == 1;
    } catch (NumberFormatException e) {
        return false;
    }
}
        * */

}
