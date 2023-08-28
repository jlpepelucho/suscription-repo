package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoDeducible;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoDeducibleRepository extends JpaRepository<EndosoDeducible, String> {
    @Query(value = "SELECT ed FROM EndosoDeducible ed WHERE ed.endosoid IN (SELECT ei.endosoId FROM EndosoItem ei WHERE ei.endosoId=:endosoId AND ei.id=:endosoItemid) AND ed.criterioDeducibleId=:criterioDeducibleId")
    Optional<List<EndosoDeducible>> getEndosoDeducibleByEndosoidAndCriterioDeducibleIdAndEndosoItemId(@Param("endosoId") String endosoid,@Param("criterioDeducibleId") String criterioDeducibleId,@Param("endosoItemid")String endosoItemid);
    @Query(value = "SELECT ed FROM EndosoDeducible ed WHERE ed.endosoid IN (SELECT ei.endosoId FROM EndosoItem ei WHERE  ei.id IN (SELECT ei.endosoItemId FROM EndosoItemCobertura ei WHERE  ei.id=:endosoItemCoberturaid)) AND ed.criterioDeducibleId=:criterioDeducibleId")
    Optional<List<EndosoDeducible>> getEndosoDeducibleByCriterioDeducibleIdAndEndosoItemCoberturaId(@Param("criterioDeducibleId") String criterioDeducibleId,@Param("endosoItemCoberturaid")String endosoItemid);

    Optional<List<EndosoDeducible>> getEndosoDeducibleByEndosoidAndCriterioDeducibleIdAndClaseid(String endosoid, String criterioDeducibleId, @Size(max = 16) String claseid);

    @Transactional()
    @Modifying
    @Query("DELETE FROM EndosoDeducible d WHERE d.id = :id")
    void deleteEndosoDeducibleById(@Param("id") String id);
}