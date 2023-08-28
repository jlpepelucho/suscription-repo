package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Extra;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtraRepository extends JpaRepository<Extra, String> {
    Optional<List<Extra>> getExtraByVehiculoidAndEstaincluido(String vehiculoid, @NotNull Boolean estaincluido);
}