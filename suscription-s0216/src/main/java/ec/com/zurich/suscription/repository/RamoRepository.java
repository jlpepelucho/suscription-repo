package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.Ramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RamoRepository extends JpaRepository<Ramo, String> {
    Optional<Ramo> getRamoById(String id);
}
