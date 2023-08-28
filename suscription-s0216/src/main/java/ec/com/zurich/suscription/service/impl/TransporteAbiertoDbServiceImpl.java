package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.TransporteAbiertoRepository;
import ec.com.zurich.suscription.resources.entity.TransporteAbierto;
import ec.com.zurich.suscription.service.TransporteAbiertoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransporteAbiertoDbServiceImpl implements TransporteAbiertoDbService {

    private final TransporteAbiertoRepository transporteAbiertoRepository;

    @Override
    @Transactional(readOnly = true)
    public TransporteAbierto consultarTransporteAbiertoPorItemId(String itemId, Boolean retornaNull) {
        Optional<TransporteAbierto> transporteAbiertoOptional = transporteAbiertoRepository.findById(itemId);
        if (retornaNull) {
            return transporteAbiertoOptional.orElse(null);
        }
        return transporteAbiertoOptional.orElse(new TransporteAbierto());
    }


}
