package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface DemoDbRepository extends JpaRepository<VariablesSistema, String> {

    Stream<VariablesSistema> getByNombreIn(List<String> nombres);

}