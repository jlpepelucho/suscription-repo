package ec.com.zurich.suscription.repository;

import ec.com.zurich.suscription.resources.entity.EquipoElectronico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoElectronicoRepository extends JpaRepository<EquipoElectronico, String> {
    Optional<EquipoElectronico> getEquipoElectronicoById(String s);
}