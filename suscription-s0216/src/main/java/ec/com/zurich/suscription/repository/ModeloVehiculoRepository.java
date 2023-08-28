package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ModeloVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeloVehiculoRepository extends JpaRepository<ModeloVehiculo, String> {
    Optional<ModeloVehiculo> getModeloVehiculoById(String s);

}