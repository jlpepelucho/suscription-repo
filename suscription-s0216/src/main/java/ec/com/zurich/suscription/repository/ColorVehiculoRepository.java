package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ColorVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorVehiculoRepository extends JpaRepository<ColorVehiculo, String> {

    Optional<ColorVehiculo> getColorVehiculoById(String s);
}