package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EndosoItemCoberturaTexto;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EndosoItemCoberturaTextoRepository extends JpaRepository<EndosoItemCoberturaTexto, String> {
    Optional<List<EndosoItemCoberturaTexto>> getEndosoItemCoberturaTextoByEndosoitemcoberturaidAndCoberturatextoid(String endosoitemcoberturaid, @Size(max = 16) String coberturatextoid);
    Optional<List<EndosoItemCoberturaTexto>> getEndosoItemCoberturaTextoByEndosoitemcoberturaid(String endosoitemcoberturaid);

    @Transactional
    @Modifying
    @Query("update EndosoItemCoberturaTexto e set e = ?1 where e.id = ?2")
    void updateEndosoItemCoberturaTextoById(EndosoItemCoberturaTexto endosoItemCoberturaTexto , String id);

    @Transactional()
    @Modifying
    @Query("DELETE FROM EndosoItemCoberturaTexto d WHERE d.id = :id")
    void deleteEndosoItemCoberturaTextoById(@Param("id") String id);
}