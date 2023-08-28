package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.DeducibleDetalleProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeducibleDetalleProductoEntityRepository extends JpaRepository<DeducibleDetalleProductoEntity, String> {
    Optional<List<DeducibleDetalleProductoEntity>> getDeducibleDetalleProductoEntityByDetproductoid(String s);
}