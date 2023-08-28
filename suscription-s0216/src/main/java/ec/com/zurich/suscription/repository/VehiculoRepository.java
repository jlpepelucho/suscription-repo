package ec.com.zurich.suscription.repository;
import ec.com.zurich.suscription.resources.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    Optional<Vehiculo> getVehiculoById(String id);
    Optional<List<Vehiculo>> getVehiculoByChasisAndMotor(String chasis, String motor);

    @Transactional
    @Modifying
    @Query("update Vehiculo e set e = ?1 where e.id = ?2")
    void updateVehiculoById(Vehiculo endosoItem,String id);
}