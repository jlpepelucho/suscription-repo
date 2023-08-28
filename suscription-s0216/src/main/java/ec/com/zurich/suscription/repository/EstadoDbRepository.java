package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoDbRepository extends JpaRepository<Estado, String> {
    Optional<Estado> getEstadoById(String id);

   }
