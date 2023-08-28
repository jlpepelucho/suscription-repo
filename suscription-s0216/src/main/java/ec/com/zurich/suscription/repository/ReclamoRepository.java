package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReclamoRepository extends JpaRepository<Reclamo, String> {

    @Query("select distinct r.numeroReclamo from Reclamo r, EndosoItem ei , Vehiculo v "
            +"where r.endosoItemId=ei.id and v.chasis=:chasis and v.id=ei.itemId "
            +"and lower(r.tipoPerdida) like '%total%' ")
    Optional<List<Object[]>> getDistinctNumeroReclamoByChasis(
            @Param("chasis") String chasis);

}