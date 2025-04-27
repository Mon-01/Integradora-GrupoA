package grupo.a.modulocomun.Entidades.Auxiliares;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class DatosBancarios {

    @Id
    private Long id;
    private String entidadBancaria;
    private String numCuenta;

    @Embedded
    private TarjetaCredito tarjeta;
}
