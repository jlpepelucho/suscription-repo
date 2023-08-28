package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.Predio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredioRepository extends JpaRepository<Predio, String> {
}
