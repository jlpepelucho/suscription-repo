package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoDeducibleTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoDeducibleTipoRepository extends JpaRepository<EndosoDeducibleTipo, String> {
    Optional<List<EndosoDeducibleTipo>> getEndosoDeducibleTipoByEndosoDeducibleId(String s);
    @Transactional()
    @Modifying
    @Query("DELETE FROM EndosoDeducibleTipo d WHERE d.id = :id")
    void deleteEndosoDeducibleTipoById(@Param("id") String id);
}