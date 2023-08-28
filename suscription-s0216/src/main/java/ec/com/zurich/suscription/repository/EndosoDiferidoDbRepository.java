package ec.com.zurich.suscription.repository;



import ec.com.zurich.suscription.resources.entity.EndosoDiferido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoDiferidoDbRepository extends JpaRepository<EndosoDiferido, String> {
    @Transactional
    @Modifying
    @Query("update EndosoDiferido e set e.estadoId = ?1 where e.id = ?2")
    void updateEstadoIdById(String estadoId , String id);
    @Transactional
    @Modifying
    @Query("update EndosoDiferido e set e = ?1 where e.id = ?2")
    void updateEndosoDiferidoById(EndosoDiferido endosoDiferido , String id);

    Optional<List<EndosoDiferido>> getEndosoDiferidoById(String Id);
    @Query("SELECT ee from EndosoDiferido ee where ee.endosoId=:endosoid order by ee.anio")
    Optional<List<EndosoDiferido>> getEndosoDiferidoByEndosoId(@Param("endosoid")String Id);
    @Query("SELECT e FROM EndosoDiferido e WHERE e.endoso.id = :endosoId AND e.anio = :anio ORDER BY e.anio")
    Optional<List<EndosoDiferido>> getEndosoDiferidoByEndosoIdAndAnio(@Param("endosoId") String endosoId, @Param("anio") int anio);

}
