package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.PlantillaDeducible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantillaDeducibleRepository extends JpaRepository<PlantillaDeducible, String> {
    Optional<List<PlantillaDeducible>> getPlantillaDeducibleByPlantillaidAndCriteriodeducibleid(String plantillaid, String criteriodeducibleid);
}