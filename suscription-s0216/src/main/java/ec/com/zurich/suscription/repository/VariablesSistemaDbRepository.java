package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface VariablesSistemaDbRepository extends JpaRepository<VariablesSistema, String> {
    Optional<VariablesSistema> getVariablesSistemaById(String id);

    Optional<VariablesSistema> getVariablesSistemaByNombreAndEsGlobalAndCompaniaSegurosIdAndSucursalId(
            String nombre, Boolean esGlobal, String companiaSegurosId, String sucursalId);

    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndEsGlobalAndCompaniaSegurosId(String nombre,
                                                                                                Boolean esGlobal, String companiaSegurosId);

    @Query("SELECT p FROM VariablesSistema p WHERE p.nombre = :nombre AND (:sucursalIsNull != true OR p.sucursalId = :sucursalId) AND p.companiaSegurosId = :companiaSegurosId")
    Optional<List<VariablesSistema>> findByNombreAndSucursalIdAndCompaniaSegurosId(@Param("nombre") String nombre,
                                                                                   @Param("sucursalId") String sucursalId, @Param("companiaSegurosId") String companiaSegurosId,
                                                                                   @Param("sucursalIsNull") boolean sucursalIsNull);

    @Query("SELECT p FROM VariablesSistema p WHERE p.nombre = :texto AND p.esGlobal = :esGlobal")
    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndEsGlobal(@Param("texto") String nombre,
                                                                            @Param("esGlobal") Boolean esGlobal);

    @Query("Select v from VariablesSistema v " + "  where v.esGlobal = :esglobal "
            + "  and v.nombre = :nombre " + "  and v.companiaSegurosId = :companiaSegurosId " +
            " and v.sucursalId is null")
    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndCompaniaSegurosAndEsGlobal(
            @Param("nombre") String nombre, @Param("companiaSegurosId") String companiaSegurosId,
            @Param("esglobal") Boolean esGlobal);

    @Query("SELECT vs FROM VariablesSistema vs WHERE vs.esGlobal = false AND"
            + "  vs.puntoEmision =:puntoEmision " + " AND vs.nombre LIKE :nemonico"
            + " AND vs.esAuditable = true AND vs.activa = true"
            + " AND vs.companiaSegurosId =:laCompaniaSegurosId "
            + " AND vs.sucursalId =:laSucursalId ")
    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndPuntoEmisionAndCompaniAndSucursal(
            @Param("puntoEmision") String puntoEmision, @Param("nemonico") String nemonico,
            @Param("laCompaniaSegurosId") String laCompaniaSegurosId,
            @Param("laSucursalId") String laSucursalId);

    @Query("SELECT vs FROM VariablesSistema vs WHERE vs.esGlobal = false AND"
            + "  vs.puntoEmision =:puntoEmision " + " AND vs.nombre = 'NROFACTANT' "
            + " AND vs.esAuditable = false "
            + " AND vs.companiaSegurosId =:laCompaniaSegurosId "
            + " AND vs.sucursalId =:laSucursalId ")
    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndPuntoEmisionAndCompaniAndSucursal(
            @Param("puntoEmision") String puntoEmision,
            @Param("laCompaniaSegurosId") String laCompaniaSegurosId,
            @Param("laSucursalId") String laSucursalId);

    @Query("Select v from VariablesSistema v " + "  where v.esGlobal = :esglobal "
            + "  and v.nombre = :nombre " + "  and v.companiaSegurosId = :companiaSegurosId " +
            " and v.sucursalId = :sucursalId")
    Optional<List<VariablesSistema>> getVariablesSistemaByNombreAndCompaniaSegurosAndEsGlobalAndSucursal(
            @Param("nombre") String nombre, @Param("companiaSegurosId") String companiaSegurosId,
            @Param("esglobal") Boolean esGlobal, @Param("sucursalId") String sucursalId);

    @Query("SELECT p FROM VariablesSistema p WHERE p.nombre = :texto AND p.companiaSegurosId = :companiaSId AND p.esGlobal = :esGlobal")
    Optional<VariablesSistema> getVariablesSistemaByNombreAndCompaniaSegurosIdAndEsGlobal(
            @Param("texto") String nombre, @Param("companiaSId") String companiaSeguroId,
            @Param("esGlobal") Boolean esGlobal);

    @Query("SELECT vs.id,vs.activa, vs.nombre FROM VariablesSistema vs WHERE vs.esGlobal = false AND "
            + " vs.puntoEmision = :puntoEmision AND vs.nombre = 'NROFACTANT' AND vs.esAuditable = true   AND vs.companiaSegurosId = "
            + ":laCompaniaSegurosId AND vs.sucursalId = :laSucursalId")
    List<Object[]> getVariablesSistemaIdActivaNOmbreByPuntoEmisionNombreCompaniaSucursal(
            @Param("puntoEmision") String puntoEmision,
            @Param("laCompaniaSegurosId") String companiaSeguroId,
            @Param("laSucursalId") String laSucursalId);

    @Query("SELECT vs.id,vs.activa, vs.nombre FROM VariablesSistema vs WHERE vs.esGlobal = false AND "
            + " vs.puntoEmision = :puntoEmision AND vs.nombre LIKE :numero AND vs.esAuditable = true AND vs.activa = true AND vs.companiaSegurosId = "
            + ":laCompaniaSegurosId AND vs.sucursalId = :laSucursalId")
    List<Object[]> getVariablesSistemaIdActivaNOmbreByPuntoEmisionNombreCompaniaSucursal(
            @Param("puntoEmision") String puntoEmision,
            @Param("laCompaniaSegurosId") String companiaSeguroId,
            @Param("laSucursalId") String laSucursalId, @Param("numero") String numero);

    @Query(value = "SELECT p.valor, p.nombreSecuencia FROM VariablesSistema p WHERE (:#{#sqlParams[0]}) = :#{#sqlParams[2]}", nativeQuery = true)
    List<Object[]> findBySql(@Param("sqlParams") List<String[]> sqlParams);

    @Query(value = "SELECT p.id FROM VariablesSistema p WHERE (:#{#sqlParams[0]}) =  :#{#sqlParams[2]}", nativeQuery = true)
    Optional<List<Object>> findBySqlID(@Param("sqlParams") List<String[]> sqlParams);

    @Transactional()
    @Modifying
    @Query("UPDATE VariablesSistema e SET e = :variablesSistema WHERE e.id = :id")
    void updateVariablesSistema(@Param("id") String id,
                                @Param("variablesSistema") VariablesSistema variablesSistema);

    @Transactional()
    @Modifying
    @Query("UPDATE VariablesSistema e SET e.valor = :variablesSistema WHERE e.id = :id")
    void updateVariablesSistemaValorById(@Param("id") String id,
                                         @Param("variablesSistema") BigDecimal variablesSistema);

    @Query(value = "SELECT vs.valor "
            + "FROM VariablesSistema vs "
            + "WHERE vs.nombre = :nombreVariable "
            + "AND vs.companiaSegurosId = :companiaSegurosId "
            + "AND vs.esGlobal = :esGlobal"
    )
    Optional<BigDecimal> consultarCantidadDeVariablesPorVariableyCompaniaDeSegurosId(
            @Param("nombreVariable") String nombreVariable,
            @Param("esGlobal") Boolean esGlobal,
            @Param("companiaSegurosId") String companiaSegurosId
    );


}