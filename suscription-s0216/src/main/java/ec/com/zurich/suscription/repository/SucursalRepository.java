package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, String> {
    Optional<Sucursal> getSucursalById(String s);
}
