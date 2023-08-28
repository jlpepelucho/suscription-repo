package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {

    @Transactional
    @Modifying
    @Query("update Item e set e = ?1 where e.id = ?2")
    void updateItemById(Item endosoItem, String id);
    Optional<Item> getItemById(String id);

}