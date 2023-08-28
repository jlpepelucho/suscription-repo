package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.CoberturaTexto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoberturaTextoRepository extends JpaRepository<CoberturaTexto, String> {
    Optional<List<CoberturaTexto>> getCoberturaTextoByCoberturaid(String s);
}