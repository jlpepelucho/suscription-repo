package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Poliza;
import ec.com.zurich.suscription.resources.model.EndosoItemDbResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface PolizaRepository extends JpaRepository<Poliza, String> {
    Optional<Poliza> getPolizaById(String id);
    @Transactional
    @Modifying
    @Query("update Poliza e set e = ?1 where e.id = ?2")
    void updatePolizaById(Poliza poliza , String id);

    @Query(value = "SELECT NEW ec.com.zurich.suscription.resources.model.EndosoItemDbResponse(po.conjuntoCoberturas.id, po.configProductoId) "
            + "FROM Poliza po "
            + "WHERE po.fechaElaboracion IN "
            + "(SELECT MIN(pol.fechaElaboracion) "
            + "FROM Poliza pol "
            + "WHERE pol.numero = :contenedor "
            + "AND pol.tipoContenedor = 'A')"
    )
    Optional<EndosoItemDbResponse> consultarListaItemProducto(
            @Param("contenedor") String contenedor
    );
    @Query(" SELECT sum(a.cantidad) as cantidadExcluida "
            + " FROM Poliza x, Endoso y, TipoEndoso z, EndosoItem a, Item b, Blanket c, EndosoEstado e "
            + " WHERE x.id = y.polizaId and  y.id = a.endosoId and  z.id = y.tipoEndosoId and "
            + " a.itemId = b.id and  b.id = c.id and e.endosoId = y.id and x.id = :polizaId "
            + " and  z.nemotecnico IN ('POL','AVA','RVA','INC') and e.estadoId in ('13','15','12') and "
            + " e.esActual=true and b.id = :itemId "
    )
    Optional<BigDecimal> getSumCantidadByPolizaIdAndItemId(
            @Param("polizaId") String polizaId,@Param("itemId") String itemId
    );

    @Query(" SELECT sum(d.limiteCobertura) as valActualTasaCobertura"
            + " FROM Poliza a, Endoso b, TipoEndoso z, EndosoItem c, EndosoItemCobertura d, EndosoEstado e "
            + " WHERE a.id = b.polizaId and b.id = c.endosoId and e.endosoId = b.id and "
            + " e.esActual = true and e.estadoId in('12','13','15') and c.id = d.endosoItemId and "
            + " c.id = d.endosoItemId and z.id = b.tipoEndosoId and c.sisuma = '1' and "
            + " b.esAjusteVigencia <> true and z.nemotecnico in ('POL','INC','AVA','RVA') and a.id = :polizaId "
            + " and d.coberturaId = :coberturaId and c.itemId = :itemId "
    )
    Optional<BigDecimal> getSumLimiteCoberturaByPolizaIdAndItemIdAndCoberturaId(
            @Param("polizaId") String polizaId,@Param("itemId") String itemId,@Param("coberturaId") String coberturaId
    );
    @Query(value = "SELECT po.configProductoId "
            + "FROM Poliza po "
            + "WHERE po.numero = :contenedor"
    )
    Optional<String> consultarProductoIdByContenedor(
            @Param("contenedor") String contenedor
    );
}