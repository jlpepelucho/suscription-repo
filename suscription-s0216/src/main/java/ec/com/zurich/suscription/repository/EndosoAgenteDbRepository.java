package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EndosoAgenteDbRepository extends JpaRepository<EndosoAgente, String> {
    @Query("SELECT ea FROM EndosoAgente ea WHERE ea.agenteId=:agenteId AND ea.endosoId IN (SELECT e.id FROM Endoso e where e.polizaId IN (SELECT p.id FROM Poliza p where " +
            "p.numero= :contenedor and p.ramoId=:ramoId))")
    Optional<List<EndosoAgente>> getEndosoAgenteByEndosoIdInPoliza(@Param("contenedor") String contenedor,@Param("ramoId") String ramoId,@Param("agenteId") String agenteId);

    @Query("SELECT ea FROM EndosoAgente ea WHERE ea.tipoAgente=:tipoAgenteId AND ea.endosoId =:endosoId")
    Optional<List<EndosoAgente>> getEndosoAgenteByEndosoIdAndTipoAgente(@Param("tipoAgenteId") String tipoAgenteId,@Param("endosoId") String endosoId);


}
