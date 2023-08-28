package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.DetalleProducto;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, String> {
    @Query(" SELECT d.id"
            +" FROM DetalleProducto d,Coberturasconjunto co,ConjuntoCoberturas cj "
            +" WHERE d.planid = :planId AND cj.id = :conjuntoId"
            +" AND cj.id=co.conjuntocoberid  AND d.coberturasConjuntoId=co.id"
    )
    Optional<List<String>> getDetalleProductoByPlanIdAndConjuntoId(
            @Param("planId") String planId,
            @Param("conjuntoId") String conjuntoId
    );
    Optional<DetalleProducto> getDetalleProductoById(@Size(max = 16) String id);
}