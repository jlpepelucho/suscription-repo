package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, String> {
}