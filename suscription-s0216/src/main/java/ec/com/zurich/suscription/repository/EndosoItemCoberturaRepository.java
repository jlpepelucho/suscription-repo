package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.EndosoItemCobertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EndosoItemCoberturaRepository extends JpaRepository<EndosoItemCobertura, String> {


    @Query(value = "SELECT SUM(eic.limite) "
            + "FROM EndosoItemCobertura eic "
            + "WHERE eic.endosoItem.itemId = :itemId "
            + "AND eic.endosoItem.endosoId IN (SELECT ee.endosoId FROM EndosoEstado ee WHERE ee.estadoId IN ('12','13','15') AND ee.esActual = true) "
            + "AND eic.endosoItem.endoso.esAjusteVigencia <> true "
            + "AND eic.endosoItem.endoso.tipoEndoso.nemotecnico IN ('POL','INC','AVA','RVA', 'APL', 'RNV') "
            + "AND eic.endosoItem.endoso.polizaId = :polizaId "
            + "AND eic.coberturaId = :coberturaId"
    )
    Optional<BigDecimal> consultarValorLimiteActual(
            @Param("polizaId") String polizaId,
            @Param("itemId") String itemId,
            @Param("coberturaId") String coberturaId
    );

    @Query(value = "SELECT SUM(eic.limiteCobertura) "
            + "FROM EndosoItemCobertura eic "
            + "WHERE eic.endosoItem.itemId = :itemId "
            + "AND eic.endosoItem.endosoId IN (SELECT ee.endosoId FROM EndosoEstado ee WHERE ee.estadoId IN ('12','13','15') AND ee.esActual = true) "
            + "AND eic.endosoItem.endoso.esAjusteVigencia <> true "
            + "AND eic.endosoItem.endoso.tipoEndoso.nemotecnico IN ('POL','INC','AVA','RVA') "
            + "AND eic.endosoItem.endoso.polizaId = :polizaId "
            + "AND eic.coberturaId = :coberturaId"
    )
    Optional<BigDecimal> consultarValorLimiteCoberturaActual(
            @Param("polizaId") String polizaId,
            @Param("itemId") String itemId,
            @Param("coberturaId") String coberturaId
    );

    Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemId(String s);

    @Transactional()
    @Modifying
    @Query("DELETE FROM EndosoItemCobertura d WHERE d.id = :id")
    void deleteEndosoItemCoberturaById(@Param("id") String id);
}
