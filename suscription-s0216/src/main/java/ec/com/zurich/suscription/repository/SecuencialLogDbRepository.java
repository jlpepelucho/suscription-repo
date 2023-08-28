package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.SecuencialLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecuencialLogDbRepository extends JpaRepository<SecuencialLog, String> {

}
