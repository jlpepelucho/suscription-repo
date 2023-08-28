package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.TransporteRepository;
import ec.com.zurich.suscription.resources.entity.Transporte;
import ec.com.zurich.suscription.service.TransporteDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransporteDbServiceImpl implements TransporteDbService {

    private final TransporteRepository transporteRepository;

    @Override
    @Transactional(readOnly = true)
    public Transporte consultarTransportePorItemId(String itemId) {
        Optional<Transporte> transporteOptional = transporteRepository.findById(itemId);
        return transporteOptional.orElse(null);
    }

}
