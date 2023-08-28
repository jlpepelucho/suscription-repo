package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoEstadoRepository extends JpaRepository<EndosoEstado, String> {
    @Transactional
    @Modifying
    @Query("update EndosoEstado e set e = ?1 where e.id = ?2")
    void updateEndosoEstadoById(EndosoEstado endosoDiferido , String id);

    Optional<List<EndosoEstado>> getEndosoEstadoByEndosoIdAndEsActual(String endosoId, Boolean esActual);

}