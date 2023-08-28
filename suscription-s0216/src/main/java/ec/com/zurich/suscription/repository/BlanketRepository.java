package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Blanket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlanketRepository extends JpaRepository<Blanket, String> {
    Optional<Blanket> getBlanketById(String s);
    @Transactional
    @Modifying
    @Query("update Blanket e set e = ?1 where e.id = ?2")
    void updateBlanketById(Blanket blanket , String id);
}
