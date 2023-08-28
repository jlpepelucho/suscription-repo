package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.VehiculoRepository;
import ec.com.zurich.suscription.resources.entity.Vehiculo;
import ec.com.zurich.suscription.service.VehiculoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VehiculoDbServiceImpl implements VehiculoDbService {

    private final VehiculoRepository vehiculoRepository;

    @Override
    @Transactional(readOnly = true)
    public Vehiculo consultarVehiculoPorItemId(String itemId) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.getVehiculoById(itemId);
        return vehiculoOptional.orElse(null);
    }

}
