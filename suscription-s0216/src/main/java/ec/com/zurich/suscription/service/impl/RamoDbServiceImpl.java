package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.RamoRepository;
import ec.com.zurich.suscription.resources.entity.Ramo;
import ec.com.zurich.suscription.service.RamoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RamoDbServiceImpl implements RamoDbService {

    private final RamoRepository ramoRepository;

    @Override
    @Transactional(readOnly = true)
    public Ramo consultarRamoPorId(String ramoId) {
        Optional<Ramo> ramo = ramoRepository.getRamoById(ramoId);
        return ramo.orElse(null);

    }

}
