package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.TipoDocAutorizacionSRI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoDocAutorizacionSRIDbRepository extends JpaRepository<TipoDocAutorizacionSRI, String> {

    Optional<TipoDocAutorizacionSRI> getTipoDocAutorizacionSRIById(String Id);

}
