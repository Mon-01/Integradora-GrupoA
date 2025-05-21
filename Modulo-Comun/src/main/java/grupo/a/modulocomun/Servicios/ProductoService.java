package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.Auxiliares.ProductoLibroDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoMuebleDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoRopaDTO;
import grupo.a.modulocomun.DTO.ProductoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.Categoria;
import grupo.a.modulocomun.Entidades.Auxiliares.ProductoLibro;
import grupo.a.modulocomun.Entidades.Auxiliares.ProductoMueble;
import grupo.a.modulocomun.Entidades.Auxiliares.ProductoRopa;
import grupo.a.modulocomun.Entidades.Producto;
import grupo.a.modulocomun.Repositorios.DistribucionProductoRepository;
import grupo.a.modulocomun.Repositorios.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private DistribucionProductoRepository distribucionProductoRepository;
    @Autowired
    private DistribucionProductoIdRepository distribucionProductoIdRepository;

    public void eliminarProducto(Long id) {
        distribucionProductoRepository.deleteById_ProductoId(id);
        productoRepository.deleteById(id);
    }

    public void eliminarProductos(List<Long> ids) {
        distribucionProductoRepository.deleteByProducto_Ids(ids);
        productoRepository.deleteAllByIdInBatch(ids);
    }

    public Optional<Producto> buscarProducto(Long id) {
        return productoRepository.findById(id);
    }

    public ProductoDTO convertirADTO(Producto producto) {
        if (producto instanceof ProductoMueble mueble) {
            ProductoMuebleDTO muebleDTO = new ProductoMuebleDTO();
            copiarDatosComunesADTO(muebleDTO, producto); // Copia los datos comunes
            copiarDatosEspecificosMuebleADTO(muebleDTO, mueble); // Copia los datos específicos
            return muebleDTO;
        } else if (producto instanceof ProductoLibro libro) {
            ProductoLibroDTO libroDTO = new ProductoLibroDTO();
            copiarDatosComunesADTO(libroDTO, producto);
            copiarDatosEspecificosLibroADTO(libroDTO, libro);
            return libroDTO;
        } else if (producto instanceof ProductoRopa ropa) {
            ProductoRopaDTO ropaDTO = new ProductoRopaDTO();
            copiarDatosComunesADTO(ropaDTO, producto);
            copiarDatosEspecificosRopaADTO(ropaDTO, ropa);
            return ropaDTO;
        } else {
            throw new IllegalArgumentException("Tipo de producto desconocido: " + producto.getClass().getName());
        }
    }

    // Método para copiar los datos comunes de la entidad Producto al ProductoDTO
    private void copiarDatosComunesADTO(ProductoDTO dto, Producto producto) {
        dto.setId_producto(producto.getId_producto());
        dto.setDescripcion(producto.getDescripcion());
        dto.setMarca(producto.getMarca());
        dto.setEsPerecedero(producto.getEsPerecedero());
        dto.setPrecio(producto.getPrecio());
        dto.setNombre(producto.getNombre());
        dto.setValoracion(producto.getValoracion());

        if (producto.getFechaAlta() != null) {
            // java.sql.Date tiene un método directo toLocalDate()
            dto.setFechaFabricacion(((java.sql.Date) producto.getFechaAlta()).toLocalDate());
        } else {
            dto.setFechaFabricacion(null); // O maneja como prefieras si la fecha es nula
        }

        // Conversión de List<Categoria> a List<String>
        if (producto.getCategorias() != null) {
            dto.setCategorias(producto.getCategorias().stream()
                    .map(Categoria::getNombre) // Asumiendo que Categoria tiene un método getNombre()
                    .collect(java.util.ArrayList::new, java.util.ArrayList::add, java.util.ArrayList::addAll));
        }

        // Establece el tipo de producto para el DTO según la clase de la entidad
        if (producto instanceof ProductoMueble) {
            dto.setTipoProducto("MUEBLE");

        } else if (producto instanceof ProductoLibro) {
            dto.setTipoProducto("LIBRO");
        } else if (producto instanceof ProductoRopa) {
            dto.setTipoProducto("ROPA");
        }
    }

    // Métodos para copiar los datos específicos de cada tipo de producto
    private void copiarDatosEspecificosMuebleADTO(ProductoMuebleDTO muebleDTO, ProductoMueble mueble) {
        muebleDTO.setAncho(mueble.getAncho());
        muebleDTO.setAlto(mueble.getAlto());
        muebleDTO.setProfundo(mueble.getProfundo());
        muebleDTO.setColores(mueble.getColores());
        // Otros campos específicos de ProductoMueble si existen
    }

    private void copiarDatosEspecificosLibroADTO(ProductoLibroDTO libroDTO, ProductoLibro libro) {
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor());
        libroDTO.setEditorial(libro.getEditorial());
        libroDTO.setTapa(libro.getTapa());
        libroDTO.setNumeroPaginas(libro.getNumeroPaginas());
        libroDTO.setSegundaMano(libro.isSegundaMano());
        // Otros campos específicos de ProductoLibro si existen
    }

    private void copiarDatosEspecificosRopaADTO(ProductoRopaDTO ropaDTO, ProductoRopa ropa) {
        ropaDTO.setTalla(ropa.getTalla());
        ropaDTO.setMaterial(ropa.getMaterial());
        ropaDTO.setEstacion(ropa.getEstacion());
        // Otros campos específicos de ProductoRopa si existen
    }

}
