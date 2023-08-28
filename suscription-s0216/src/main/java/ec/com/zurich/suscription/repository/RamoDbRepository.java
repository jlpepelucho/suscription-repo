package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Ramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RamoDbRepository extends JpaRepository<Ramo, String> {
    Optional<List<Ramo>> getRamoById(String id);
}
