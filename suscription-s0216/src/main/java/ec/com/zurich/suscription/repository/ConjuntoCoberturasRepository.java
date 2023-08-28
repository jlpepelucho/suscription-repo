package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.ConjuntoCoberturas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConjuntoCoberturasRepository extends JpaRepository<ConjuntoCoberturas, String> {
    Optional<ConjuntoCoberturas> getConjuntoCoberturasById(String s);
}