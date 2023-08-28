package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoItemRepository extends JpaRepository<TipoItem, String> {
    Optional<List<TipoItem>> getTipoItemsByNombre(String nombre);
    Optional<TipoItem> getTipoItemById(String id);
}