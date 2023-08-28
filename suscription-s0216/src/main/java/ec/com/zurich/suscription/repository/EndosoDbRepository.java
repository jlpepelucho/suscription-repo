package ec.com.zurich.suscription.repository;



import ec.com.zurich.suscription.resources.entity.Endoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoDbRepository extends JpaRepository<Endoso, String> {

    Optional<Endoso> getEndosoById(String id);
    @Query(" SELECT eic.afectaPrima as afectaPrima, " +
            "eic.primaCoberturaNeta as valorPrimaCobertura, " +
            "ei.endosoId as  endosoId, " +
            "eic.porcentajeComisionNormal as porcentajeCobertura, " +
            "eic.porcentajeComisionEspecial as porcentajeComisionEspecial " +
            " FROM Endoso e, EndosoItem ei, EndosoItemCobertura eic" +
            " WHERE e.id = ei.endosoId " +
            " AND ei.id = eic.endosoItemId " +
            " AND eic.afectaPrima = true " +
            " AND e.id =:elEndosoId")
    Optional<List<Object[]>> getAllByEndosoId(@Param("elEndosoId") String elEndosoId);

    @Query("select en from Endoso en join fetch en.poliza p join fetch en.tipoEndoso t "
            + "join fetch en.tipoItem ti "
            + "join fetch en.unidadNegocio u "
            + "where p.id = :polizaId and "
            + "t.nemotecnico = :nemotecnico ")
    Optional<List<Endoso>> getEndosoByPolisaIdAndNemotecnico(@Param("polizaId") String polizaId,@Param("nemotecnico") String nemotecnico);

    @Query("select en from Endoso en join fetch en.poliza p join fetch en.tipoEndoso t "
            + "join fetch en.tipoItem ti "
            + "join fetch en.unidadNegocio u "
            + "where p.id = :polizaId and "
            + "t.nemotecnico = :nemotecnico and en.esAjusteVigencia = :ajusteVigencia ")
    Optional<List<Endoso>> getEndosoByPolisaIdAndNemotecnicoAndEsAjusteVigencia(@Param("polizaId") String polizaId,@Param("nemotecnico") String nemotecnico,@Param("ajusteVigencia") Boolean ajusteVigencia);

    @Query("select en from Endoso en join fetch en.poliza p join fetch en.tipoEndoso t "
            + "join fetch en.tipoItem ti "
            + "join fetch en.unidadNegocio u "
            + "where p.id = :polizaId and "
            + "t.nemotecnico = :nemotecnico and en.esAjusteVigencia != :ajusteVigencia ")
    Optional<List<Endoso>> getEndosoByPolisaIdAndNemotecnicoAndNotEsAjusteVigencia(@Param("polizaId") String polizaId,@Param("nemotecnico") String nemotecnico,@Param("ajusteVigencia") Boolean ajusteVigencia);



    @Transactional
    @Modifying
    @Query("UPDATE Endoso e SET e = :endoso WHERE e.id = :id")
    void updateEndoso(@Param("id") String id, @Param("endoso") Endoso endosoItem);



}
