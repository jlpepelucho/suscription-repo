package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.PlantillaDeducibleTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantillaDeducibleTipoRepository extends JpaRepository<PlantillaDeducibleTipo, String> {
    Optional<List<PlantillaDeducibleTipo>> getPlantillaDeducibleTipoByPlantillaDeducibleId(String s);
}