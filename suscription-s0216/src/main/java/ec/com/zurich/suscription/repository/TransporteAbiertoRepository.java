package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.TransporteAbierto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransporteAbiertoRepository extends JpaRepository<TransporteAbierto, String> {
    @Transactional
    @Modifying
    @Query("update TransporteAbierto e set e = ?1 where e.id = ?2")
    void updateTransporteAbiertoById(TransporteAbierto transporteAbierto, String id);

    Optional<List<TransporteAbierto>> getTransporteAbiertoById(String id);
}