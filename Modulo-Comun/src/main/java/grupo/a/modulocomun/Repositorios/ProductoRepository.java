package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.Entidades.Auxiliares.DistribucionProducto;
import grupo.a.modulocomun.Entidades.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
 //   @Query("SELECT d FROM DistribucionProducto d WHERE d.proveedor.id = :proveedorId AND d.producto.descripcion = :descripcion")
 //   Optional<DistribucionProducto> findDistribucionPorProveedorYDescripcion(@Param("proveedorId") Long proveedorId, @Param("descripcion") String descripcion);

    /*
    @Transactional
    @Modifying
    @Query("INSERT INTO DistribucionProducto ...") // si no usas save()
    void saveDistribucion(DistribucionProducto distribucionProducto);

     */

//    // Método para eliminar un producto por su ID
//    @Transactional
//    void deleteById(Long id);
//
//    // Método para eliminar múltiples productos por sus IDs
//    @Transactional
//    void deleteAllByIdInBatch(Iterable<Long> ids);
}