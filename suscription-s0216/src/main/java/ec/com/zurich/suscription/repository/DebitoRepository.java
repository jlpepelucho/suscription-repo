package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Debito;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DebitoRepository extends JpaRepository<Debito, String> {

    Optional<List<Debito>> getDebitoByConvenioPagoIdAndClienteidAndEstado(String convenioPagoId, @Size(max = 16) @NotNull String clienteid, Boolean estado);
    Optional<List<Debito>> getDebitoByConvenioPagoIdAndClienteid(String convenioPagoId, @Size(max = 16) @NotNull String clienteid);


}