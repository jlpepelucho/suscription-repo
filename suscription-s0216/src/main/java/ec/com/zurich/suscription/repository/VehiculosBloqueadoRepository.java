package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.VehiculosBloqueado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculosBloqueadoRepository extends JpaRepository<VehiculosBloqueado, String> {
    @Query("select vb from VehiculosBloqueado vb "
            +"where ((vb.chasis <> ' ' and vb.chasis= :chasis) or (vb.motor <> ' ' and vb.motor= :motor"
            +") or (vb.placa <> ' ' and vb.placa= :placa)) and vb.activo = '1'")
    Optional<List<VehiculosBloqueado>> getVehiculosBloqueadoByChasisAndMotorAndPlaca(
            @Param("chasis") String chasis,@Param("motor") String motor,@Param("placa") String placa);
}