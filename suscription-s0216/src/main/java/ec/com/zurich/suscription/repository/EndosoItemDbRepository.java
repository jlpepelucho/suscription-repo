package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.EndosoItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface EndosoItemDbRepository extends JpaRepository<EndosoItem, String> {
    @Query("select count(e.id) from EndosoItem e where e.endosoId = :endosoId")
    BigDecimal countByEndosoId(@Param("endosoId") String endosoId);
    @Transactional
    @Modifying
    @Query("update EndosoItem e set e = ?1 where e.id = ?2")
    void updateEndosoItemById(EndosoItem endosoItem,String id);

    Optional<EndosoItem> getEndosoItemById(String id);
    Optional<List<EndosoItem>> getEndosoItemByEndosoId(String id);
    @Query("Select ei from EndosoItem ei "
            +" where ei.id != :id and ei.endoso.id = :endosoId "
            +" and ei.itemId = :itemId ")
    Optional<List<EndosoItem>> getEndosoItemByIdNotContainingAndEndosoIdAndItemId(@Param("id")String id,@Param("endosoId")String endosoId,@Param("itemId") String itemId);

    @Query("SELECT DISTINCT ei.id as endosoItemId,e.vigenciaDesde "
            +"FROM EndosoItem ei, Endoso e, Poliza p, Ramo r, EndosoEstado ee, Vehiculo v "
            +"WHERE p.ramoId = r.id AND ei.endosoId = e.id AND v.id=ei.itemId "
            +"AND e.esAjusteVigencia <> true AND p.id = e.polizaId "
            +"AND v.chasis = :chasis AND p.estadoId <> '9' AND r.nombre <>'SOAT' "
            +"AND ee.endosoId = e.id AND e.vigenciaHasta > :fechaF "
            +"ORDER BY e.vigenciaDesde")
    Optional<List<Object[]>> getDistinctByChasisAndFechaVigencia(@Param("chasis")String chasis,@Param("fechaF") Timestamp fecha);
    @Query("SELECT DISTINCT ei.id as endosoItemId,e.vigenciaDesde "
            +"FROM EndosoItem ei, Endoso e, Poliza p, Ramo r, EndosoEstado ee "
            +"WHERE ei.itemId = :itemId AND e.esAjusteVigencia <> true "
            +"AND ei.endosoId = e.id AND p.estadoId <> '9' AND e.polizaId = p.id "
            +"AND r.nombre <>'SOAT' AND p.ramoId = r.id AND ee.endosoId = e.id "
            +"AND e.vigenciaHasta > :fechaF ORDER BY e.vigenciaDesde")
    Optional<List<Object[]>> getDistinctByItemIdAndFechaVigencia(@Param("itemId")String itemId,@Param("fechaF") Timestamp fecha);

    @Query("SELECT DISTINCT ei.id as endosoItemId,e.vigenciaDesde,e.vigenciaHasta "
            +"FROM EndosoItem ei, Endoso e, Poliza p, Ramo r, EndosoEstado ee "
            +"WHERE ei.itemId = :itemId AND e.esAjusteVigencia <> true "
            +"AND ei.endosoId = e.id AND p.estadoId <> '9' AND e.polizaId = p.id "
            +"AND r.nombre <>'SOAT' AND p.ramoId = r.id AND ee.endosoId = e.id "
            +"AND e.vigenciaHasta > :fechaF ORDER BY e.vigenciaDesde")
    Optional<List<Object[]>> getDistinctHastaByItemIdAndFechaVigencia(@Param("itemId")String itemId,@Param("fechaF") Timestamp fecha);

    @Query("SELECT DISTINCT ei.id as endosoItemId,e.vigenciaDesde,e.vigenciaHasta "
            +"FROM EndosoItem ei, Endoso e, Poliza p, Ramo r, EndosoEstado ee, Vehiculo v "
            +"WHERE p.ramoId = r.id AND ei.endosoId = e.id AND v.id=ei.itemId "
            +"AND e.esAjusteVigencia <> true AND p.id = e.polizaId "
            +"AND v.chasis = :chasis AND p.estadoId <> '9' AND r.nombre <>'SOAT' "
            +"AND ee.endosoId = e.id AND e.vigenciaHasta > :fechaF "
            +"ORDER BY e.vigenciaDesde")
    Optional<List<Object[]>> getDistinctVigenciasHastaByChasisAndFechaVigencia(@Param("chasis")String chasis,@Param("fechaF") Timestamp fecha);


}
