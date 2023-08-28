package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.TransporteGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransporteGeneralRepository extends JpaRepository<TransporteGeneral, String> {
    Optional<TransporteGeneral> getTransporteGeneralsById(String s);
}