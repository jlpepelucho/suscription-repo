package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.HistoricoTransporteAbierto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HistoricoTransporteAbiertoRepository extends JpaRepository<HistoricoTransporteAbierto, String> {
    @Transactional
    @Modifying
    @Query("update HistoricoTransporteAbierto e set e = ?1 where e.id = ?2")
    void updateHistoricoTransporteAbiertoById(HistoricoTransporteAbierto historicoTransporteAbierto, String id);

    Optional<HistoricoTransporteAbierto> getHistoricoTransporteAbiertoById(String id);
}