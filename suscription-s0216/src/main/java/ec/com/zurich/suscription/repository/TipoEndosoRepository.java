package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.TipoEndoso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoEndosoRepository extends JpaRepository<TipoEndoso, String> {

    Optional<List<TipoEndoso>> getTipoEndosoByNemotecnico(String nemotecnico);
    Optional<TipoEndoso> getTipoEndosoById(String id);
}