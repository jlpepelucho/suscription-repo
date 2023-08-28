package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.DeducibleProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeducibleProductoRepository extends JpaRepository<DeducibleProducto, String> {
    Optional<List<DeducibleProducto>> getDeducibleProductoByConfigproductoid(String configproductoid);
}