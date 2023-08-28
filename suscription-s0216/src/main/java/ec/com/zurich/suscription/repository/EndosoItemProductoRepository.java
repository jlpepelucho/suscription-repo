package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoItemProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoItemProductoRepository extends JpaRepository<EndosoItemProducto, String> {
    boolean existsEndosoItemProductoByEndosoitemidAndConfigProductoidAndConjuntoidAndPlanid(String endosoitemid, String configProductoid, @Size(max = 16) String conjuntoid, @Size(max = 16) @NotNull String planid);

    Optional<List<EndosoItemProducto>> getEndosoItemProductoByEndosoitemid(String endosoItemId);

    @Transactional()
    @Modifying
    @Query("DELETE FROM EndosoItemProducto d WHERE d.id = :id")
    void deleteEndosoItemProductoById(@Param("id") String id);
}