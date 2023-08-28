package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.DistribucionEndosoDiferido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistribucionEndosoDiferidoDbRepository extends JpaRepository<DistribucionEndosoDiferido, String> {

    Optional<List<DistribucionEndosoDiferido>> getDistribucionEndosoDiferidoById(String id);

}
