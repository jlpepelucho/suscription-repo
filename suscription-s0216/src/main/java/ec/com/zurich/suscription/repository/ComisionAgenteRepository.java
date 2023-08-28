package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ComisionAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ComisionAgenteRepository extends JpaRepository<ComisionAgente, String> {
    @Query("select a.porcentaje " + "from ComisionAgente a, Agente b, Cobertura c "
            + "where b.id = a.agenteId " + "and c.id = a.coberturaId " + "and a.coberturaId = :coberturaId "
            + "and  a.agenteId = :agenteId")
    Optional<List<BigDecimal>> getComisionAgentePorcentajeByAgenteIdAndCoberturaId(@Param("agenteId")String AgenteId, @Param("coberturaId")String coberturaId);
}