package grupo.a.modulocomun.Entidades;

import grupo.a.modulocomun.Entidades.Auxiliares.TarjetaCredito;
import grupo.a.modulocomun.Entidades.Maestros.EntidadBancaria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor
public class DatosBancarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "entidad_bancaria_id")
    private EntidadBancaria entidadBancaria;
    private String numCuenta;


    @Embedded
    private TarjetaCredito tarjeta;
}
