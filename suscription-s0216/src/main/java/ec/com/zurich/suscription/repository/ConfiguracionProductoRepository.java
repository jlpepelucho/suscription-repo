package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ConfiguracionProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionProductoRepository extends JpaRepository<ConfiguracionProducto, String> {
    Optional<ConfiguracionProducto> getConfiguracionProductoById(String id);
}