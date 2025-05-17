package grupo.a.modulocomun.Entidades.Auxiliares;


import grupo.a.modulocomun.Validaciones.paso4.Paso4;
import grupo.a.modulocomun.Validaciones.paso4.fechaCadValidation;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@Valid
@fechaCadValidation(message = "La fecha de caducidad no puede ser anterior al mes actual",groups = Paso4.class)
public class Caducidad {
    private int anio = LocalDate.now().getYear();
    private int mes = 1;
}

