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

public interface EndosoItemCoberturaDbRepository extends JpaRepository<EndosoItemCobertura, String> {

        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemId(String endosoItemId);
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndAfectaPrima(String endosoItemId, Boolean afectaPrima);
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndAfectaPrimaAndCoberturaId(String endosoItemId, Boolean afectaPrima, String coberturaId);
        @Query("select a from EndosoItemCobertura a where a.endosoItemId=:endosoItemId AND a.afectaValorAsegurado=:afectaValorAsegurado AND a.cobertura.grupoCoberturaId=:grupoCoberturaId")
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndAfectaValorAseguradoAndCobertura_GrupoCoberturaId(@Param("endosoItemId")String endosoItemId, @Param("afectaValorAsegurado")Boolean afectaValorAsegurado,@Param("grupoCoberturaId") String cobertura_grupoCoberturaId);
        @Query("select a from EndosoItemCobertura a where a.endosoItemId=:endosoItemId AND a.afectaValorAsegurado=:afectaValorAsegurado")
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndAfectaValorAsegurado(@Param("endosoItemId")String endosoItemId, @Param("afectaValorAsegurado")Boolean afectaValorAsegurado);

        @Query("select a from EndosoItemCobertura a where a.endosoItemId=:endosoItemId AND a.cobertura.nombre=:nombreCobertura")
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndNombreCobertura(
                        @Param("endosoItemId") String endosoItemId, @Param("nombreCobertura") String nombreCobertura);

        @Query("select a from EndosoItemCobertura a where a.endosoItemId=:endosoItemId AND a.cobertura.tipoCoberturaId=:tipoCobertura")
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndTipoCobertura(
                        @Param("endosoItemId") String endosoItemId, @Param("tipoCobertura") String tipoCobertura);

        @Query("select a from EndosoItemCobertura a where a.endosoItemId=:endosoItemId AND a.cobertura.tipoCoberturaId=:tipoCobertura AND a.tieneIva=:tieneIva")
        Optional<List<EndosoItemCobertura>> getEndosoItemCoberturaByEndosoItemIdAndTipoCoberturaIdAndTieneIva(
                        @Param("endosoItemId") String endosoItemId, @Param("tipoCobertura") String tipoCobertura,
                        @Param("tieneIva") Boolean tieneIva);

        @Query("select gc.ramoId, TO_CHAR(gc.codigoContable), round((sum(eigc.valorPrimaNeta)/:valorPrima)*100,2) as porcentaje,"
                        + " sum(eigc.valorPrimaNeta) , "
                        + " (select count(distinct eigc1.grupoCoberturaId) "
                        + "    from EndosoItemGrupoCobertura eigc1"
                        + "   where eigc1.endosoDiferidoId = eigc.endosoDiferidoId ) as cont "
                        + "	from EndosoItemGrupoCobertura eigc, GrupoCobertura gc "
                        + "	where eigc.endosoDiferidoId =:endosoDiferidoId "
                        + "	and eigc.grupoCoberturaId = gc.id "
                        + "And Exists (Select 1 " + "From RamoGrupoCobertDistEndosoDif rgcded "
                        + "Where rgcded.ramoId = gc.ramoId "
                        + "And rgcded.codigoContableGrupoCobertura = gc.codigoContable "
                        + "And rgcded.estaActivo = '1') "
                        + "	group by eigc.endosoDiferidoId, gc.ramoId, gc.codigoContable")
        Optional<List<Object[]>> getEndosoItemCoberturaByEndosoDiferidoIdAndValorPrima(
                        @Param("endosoDiferidoId") String endosoDiferidoId, @Param("valorPrima") BigDecimal valorPrima);
        @Transactional
        @Modifying
        @Query("update EndosoItemCobertura e set e = ?1 where e.id = ?2")
        void updateEndosoItemCoberturaById(EndosoItemCobertura endosoItemCobertura , String id);

}
