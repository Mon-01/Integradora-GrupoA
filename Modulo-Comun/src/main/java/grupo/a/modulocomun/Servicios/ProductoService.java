package grupo.a.modulocomun.Servicios;

import grupo.a.modulocomun.Entidades.Producto;
import grupo.a.modulocomun.Repositorios.DistribucionProductoRepository;
import grupo.a.modulocomun.Repositorios.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Producto buscarProducto(Long id) {
        return productoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontro el producto con id: " + id));
    }
}
