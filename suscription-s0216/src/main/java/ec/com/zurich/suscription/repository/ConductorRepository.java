package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, String> {
    Optional<List<Conductor>> getConductorByVehiculoid(String s);
}