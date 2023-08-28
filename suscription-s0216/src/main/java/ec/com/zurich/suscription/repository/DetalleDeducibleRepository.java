package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.DetalleDeducible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleDeducibleRepository extends JpaRepository<DetalleDeducible, String> {
    Optional<List<DetalleDeducible>> getDetalleDeducibleByDeducibleid(String s);
}