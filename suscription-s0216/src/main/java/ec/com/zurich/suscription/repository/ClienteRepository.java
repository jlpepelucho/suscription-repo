package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> getClienteById(String id);
}