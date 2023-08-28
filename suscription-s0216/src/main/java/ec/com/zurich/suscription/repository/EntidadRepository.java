package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntidadRepository extends JpaRepository<Entidad, String> {
    Optional<Entidad> getEntidadById(String id);
}