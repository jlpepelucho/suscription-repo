package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.UnidadNegocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadNegocioRepository extends JpaRepository<UnidadNegocio, String> {
    Optional<UnidadNegocio> getUnidadNegocioById(String id);
}