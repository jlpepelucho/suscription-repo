package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ConvenioPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConvenioPagoRepository extends JpaRepository<ConvenioPago, String> {
    @Query("select cp.id as cid from ConvenioPago cp, CuentaBancaria cb, Banco b, Debito d "
            + "where d.convenioPagoId = cp.id " + "and d.id = :debitoId "
            + "and cp.cuentabancariaid = cb.id " + "and cb.bancoid = b.id " + "and b.nombre = 'INTERNACIONAL'")
    Optional<List<ConvenioPago>> getConvenioPagoByDebitoId(@Param("debitoId")String debitoId);
}