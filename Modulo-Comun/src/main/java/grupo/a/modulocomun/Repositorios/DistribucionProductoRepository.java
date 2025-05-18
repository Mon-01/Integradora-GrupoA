package grupo.a.modulocomun.Repositorios;

import grupo.a.modulocomun.DTO.filtros.ProductoResultadoDTO;
import grupo.a.modulocomun.Entidades.Auxiliares.DistribucionProducto;
import grupo.a.modulocomun.Entidades.Auxiliares.DistribucionProductoId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistribucionProductoRepository extends JpaRepository<DistribucionProducto, DistribucionProductoId> {
    Optional<DistribucionProducto> findById(DistribucionProductoId id);

    @Query("""
        SELECT new grupo.a.modulocomun.DTO.filtros.ProductoResultadoDTO(
            p.id_producto,
            p.descripcion,
            d.precio,
            (SELECT c.nombre FROM p.categorias c ORDER BY c.id ASC LIMIT 1),
            d.unidades,
            pr.nombre
        )
        FROM DistribucionProducto d
        JOIN d.producto p
        JOIN d.proveedor pr
        WHERE (:descripcion IS NULL OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%')))
          AND (:proveedor IS NULL OR LOWER(pr.nombre) = LOWER(:proveedor))
          AND (:esPerecedero IS NULL OR p.esPerecedero = :esPerecedero)
          AND (:categorias IS NULL OR EXISTS (
               SELECT 1 FROM p.categorias c WHERE c.nombre IN :categorias
          ))
    """)
    List<ProductoResultadoDTO> buscarProductosFiltrados(
            @Param("descripcion") String descripcion,
            @Param("proveedor") String proveedor,
            @Param("categorias") List<String> categorias,
            @Param("esPerecedero") Boolean esPerecedero
    );

    @Transactional
    void deleteById_ProductoId(Long productoId);
}