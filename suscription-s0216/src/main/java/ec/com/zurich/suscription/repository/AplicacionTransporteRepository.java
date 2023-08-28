package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.AplicacionTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AplicacionTransporteRepository extends JpaRepository<AplicacionTransporte, String> {

    @Query(value = "SELECT at.valorPrimaDeposito "
        + "FROM AplicacionTransporte at "
        + "WHERE at.id = :itemId"
    )
    Optional<BigDecimal> consultarPrimaDeposito(
            @Param("itemId") String itemId
    );

    @Query(value = "SELECT at.tasa "
            + "FROM AplicacionTransporte at "
            + "WHERE at.id = :itemId"
    )
    Optional<BigDecimal> consultarTasaCobertura(
            @Param("itemId") String itemId
    );

    Optional<List<AplicacionTransporte>> getAplicacionTransporteById(String Id);
}
