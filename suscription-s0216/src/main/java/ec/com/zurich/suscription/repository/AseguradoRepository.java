package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Asegurado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AseguradoRepository extends JpaRepository<Asegurado, String> {
}