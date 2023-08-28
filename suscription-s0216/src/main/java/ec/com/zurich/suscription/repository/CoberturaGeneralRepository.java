package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.CoberturaGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoberturaGeneralRepository extends JpaRepository<CoberturaGeneral, String> {
    Optional<CoberturaGeneral> getCoberturaGeneralById(String endosoid);
    Optional<List<CoberturaGeneral>> getCoberturaGeneralByEndosoid(String endosoid);
}