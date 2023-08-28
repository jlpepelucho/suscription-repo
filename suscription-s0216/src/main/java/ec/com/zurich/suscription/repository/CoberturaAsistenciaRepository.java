package ec.com.zurich.suscription.repository;


import ec.com.zurich.suscription.resources.entity.CoberturaAsistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoberturaAsistenciaRepository extends JpaRepository<CoberturaAsistencia, String> {

    @Query(value = "SELECT ca "
        + "FROM CoberturaAsistencia ca "
        + "WHERE ca.coberturaId = :coberturaId"
    )
    Optional<CoberturaAsistencia>  consultarCoberturaAsistenciaPorCoberturaId(
            @Param("coberturaId") String coberturaId
    );

}
