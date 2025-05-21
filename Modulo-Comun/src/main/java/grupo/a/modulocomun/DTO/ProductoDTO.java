package grupo.a.modulocomun.DTO;

import com.fasterxml.jackson.annotation.*;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoLibroDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoMuebleDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoRopaDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tipoProducto")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProductoLibroDTO.class, name = "LIBRO"),
        @JsonSubTypes.Type(value = ProductoMuebleDTO.class, name = "MUEBLE"),
        @JsonSubTypes.Type(value = ProductoRopaDTO.class, name = "ROPA")
})
public abstract class ProductoDTO {
    private Long id_producto;
    private String nombre;
    private int valoracion;
    private String tipoProducto;
    private String descripcion;
    private String marca;
    private boolean esPerecedero;
    private int unidades;
    private BigDecimal precio;
    private LocalDate fechaFabricacion;
    private List<String> categorias;
}
