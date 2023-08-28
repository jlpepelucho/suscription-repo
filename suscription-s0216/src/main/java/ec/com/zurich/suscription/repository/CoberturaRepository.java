package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Cobertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoberturaRepository extends JpaRepository<Cobertura, String> {
    @Query("select c from Cobertura c join fetch c.grupoCobertura gc where gc.ramo.id = :ramoId" +
            " and c.afectaPrima = :afectaPrima and c.afectaValorAsegurado = :afectaValorAsegurado " +
            " and c.esMasivo = :esMasivo")
    Optional<List<Cobertura>> getCoberturaByRamoIdAndAfectaPrimaAndAfectaValorAseguradoAndEsMasivo
            (@Param("ramoId")String ramoId,@Param("afectaPrima")Boolean afectaPrima,
             @Param("afectaValorAsegurado")Boolean afectaValorAsegurado, @Param("esMasivo")Boolean esMasivo);
    @Query(" SELECT x.id as idCobertura "+
            "FROM Cobertura x,TipoCobertura y, GrupoCobertura z "+
            " WHERE x.tipoCoberturaId = y.id AND "+
            " x.grupoCoberturaId = z.id AND "+
            " z.ramoId = :ramoId  AND "+
            " y .nombre = 'BASICAS' AND "+
            " x.afectaValorAsegurado <> false AND "+
            " x.afectaPrima <> false AND "+
            " x.esMasivo =  :esMasivo")
    Optional<List<String>> getCoberturaIdByRamoIdAndEsMasivo
            (@Param("ramoId")String ramoId, @Param("esMasivo")Boolean esMasivo);
    @Query(" SELECT x.id as idCobertura "+
            "FROM Cobertura x,TipoCobertura y, GrupoCobertura z "+
            " WHERE x.tipoCoberturaId = y.id AND "+
            " x.grupoCoberturaId = z.id AND "+
            " z.ramoId = :ramoId  AND "+
            " y .nombre = 'BASICAS' AND "+
            " x.afectaValorAsegurado <> false AND "+
            " x.afectaPrima <> false AND "+
            " x.esprincipalvida =  true")
    Optional<List<String>> getCoberturaIdByRamoId
            (@Param("ramoId")String ramoId);
    Optional<Cobertura> getCoberturaById(String id);

}