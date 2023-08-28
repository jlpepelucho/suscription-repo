package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoItemDetalle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EndosoItemDetalleRepository extends JpaRepository<EndosoItemDetalle, String> {
    @Transactional
    @Modifying
    @Query("update EndosoItemDetalle e set e = :endosoitemid where e.id = :id")
    void updateEndosoItemDetalleById(@Param("endosoitemid") EndosoItemDetalle endosoitemid, @Param("id") String id);

    Optional<List<EndosoItemDetalle>> getEndosoItemDetallesByEndosoitemidAndEstadoIn(@NotNull String endosoitemid, Collection<String> estado);
    @Query(value = "SELECT SUM(eid.valor) "
            + "FROM EndosoItemDetalle eid "
            + "WHERE eid.endosoitemid = :endosoItemId "
            + "AND eid.estado IN ('A', 'I')"
    )
    Optional<BigDecimal> consultarEndosoItemDetallePorEndosoItemId(
            @Param("endosoItemId") String endosoItemId
    );
}