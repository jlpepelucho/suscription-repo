package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.PlantillaCobertura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantillaCoberturaRepository extends JpaRepository<PlantillaCobertura, String> {
    Optional<List<PlantillaCobertura>> getPlantillaCoberturaByPlantillaid(String plantillaid);
    Optional<PlantillaCobertura> getPlantillaCoberturaById(String id);

}