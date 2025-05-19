package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.DTO.Auxiliares.CatalogoProductosDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoLibroDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoMuebleDTO;
import grupo.a.modulocomun.DTO.Auxiliares.ProductoRopaDTO;
import grupo.a.modulocomun.DTO.ProductoDTO;
import grupo.a.modulocomun.DTO.filtros.ProductoBusquedaDTO;
import grupo.a.modulocomun.DTO.filtros.ProductoResultadoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.*;
import grupo.a.modulocomun.Entidades.Producto;
import grupo.a.modulocomun.Repositorios.CategoriaRepository;
import grupo.a.modulocomun.Repositorios.DistribucionProductoRepository;
import grupo.a.modulocomun.Repositorios.ProductoRepository;
import grupo.a.modulocomun.Repositorios.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportacionCatalogoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final CategoriaRepository categoriaRepository;
    private final DistribucionProductoRepository distribucionProductoRepository;

    @Transactional
    public String importarCatalogo(CatalogoProductosDTO catalogo) {
        Proveedor proveedor = proveedorRepository.findByNombre(catalogo.getProveedor())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));


        int insertados = 0;

        for (ProductoDTO dto : catalogo.getProductos()) {
            validarProducto(dto);

            Producto producto = convertirADominio(dto);
            producto.setFechaAlta(new Date());
            producto.setValoracion(0);

            for (String nombreCat : dto.getCategorias()) {
                Categoria cat = categoriaRepository.findByNombre(nombreCat)
                        .orElseGet(() -> categoriaRepository.save(new Categoria(null, nombreCat)));
                producto.getCategorias().add(cat);
            }

            DistribucionProductoId distribucionId = new DistribucionProductoId(producto.getId_producto(), proveedor.getId());
            Optional<DistribucionProducto> existente = distribucionProductoRepository.findById(distribucionId);

            if (existente.isPresent()) {
                DistribucionProducto dist = existente.get();
                dist.setPrecio(dto.getPrecio());
                dist.setUnidades(dist.getUnidades() + dto.getUnidades());
                dist.setFechaFabricacion(java.sql.Date.valueOf(dto.getFechaFabricacion()));
                distribucionProductoRepository.save(dist); // <- aquí usas .save()
            } else {
                productoRepository.save(producto); // se guarda el producto primero
                DistribucionProducto nueva = new DistribucionProducto(
                        distribucionId,
                        producto,
                        proveedor,
                        dto.getPrecio(),
                        dto.getUnidades(),
                        java.sql.Date.valueOf(dto.getFechaFabricacion())
                );
                distribucionProductoRepository.save(nueva); // <- aquí también .save()
                insertados++;
            }

        }

        return insertados + " productos importados correctamente.";
    }

    private void validarProducto(ProductoDTO dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank())
            throw new IllegalArgumentException("Descripción obligatoria");

        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Precio inválido");

        if (dto.getCategorias() == null || dto.getCategorias().isEmpty())
            throw new IllegalArgumentException("Debe tener al menos una categoría");

        if (dto.getUnidades() <= 0)
            throw new IllegalArgumentException("Unidades deben ser mayores que 0");
    }

    private Producto convertirADominio(ProductoDTO dto) {
        if (dto instanceof ProductoMuebleDTO muebleDTO) {
            ProductoMueble mueble = new ProductoMueble();
            mueble.setAncho(muebleDTO.getAncho());
            mueble.setAlto(muebleDTO.getAlto());
            mueble.setProfundo(muebleDTO.getProfundo());
            mueble.setColores(muebleDTO.getColores());
            copiarDatosComunes(mueble, dto);
            return mueble;
        } else if (dto instanceof ProductoLibroDTO libroDTO) {
            ProductoLibro libro = new ProductoLibro();
            libro.setTitulo(libroDTO.getTitulo());
            libro.setAutor(libroDTO.getAutor());
            libro.setEditorial(libroDTO.getEditorial());
            libro.setTapa(libroDTO.getTapa());
            libro.setNumeroPaginas(libroDTO.getNumeroPaginas());
            libro.setSegundaMano(libroDTO.isSegundaMano());
            copiarDatosComunes(libro, dto);
            return libro;
        } else if (dto instanceof ProductoRopaDTO ropaDTO) {
            ProductoRopa ropa = new ProductoRopa();
            ropa.setTalla(ropaDTO.getTalla());
            ropa.setMaterial(ropaDTO.getMaterial());
            ropa.setEstacion(ropaDTO.getEstacion());
            copiarDatosComunes(ropa, dto);
            return ropa;
        } else {
            throw new IllegalArgumentException("Tipo de producto desconocido");
        }
    }

    private void copiarDatosComunes(Producto producto, ProductoDTO dto) {
        producto.setDescripcion(dto.getDescripcion());
        producto.setMarca(dto.getMarca());
        producto.setEsPerecedero(dto.isEsPerecedero());
        producto.setPrecio(dto.getPrecio());
        producto.setCategorias(new ArrayList<>());
    }

    public List<ProductoResultadoDTO> buscarProductos(ProductoBusquedaDTO filtro) {
        return distribucionProductoRepository.buscarProductosFiltrados(
                esVacio(filtro.getDescripcion()) ? null : filtro.getDescripcion(),
                esVacio(filtro.getProveedor()) ? null : filtro.getProveedor(),
                (filtro.getCategorias() == null || filtro.getCategorias().isEmpty()) ? null : filtro.getCategorias(),
                filtro.getEsPerecedero()
        );
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}