package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.PredioRepository;
import ec.com.zurich.suscription.resources.entity.Predio;
import ec.com.zurich.suscription.service.PredioDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PredioDbServiceImpl implements PredioDbService {

    private final PredioRepository predioRepository;

    @Override
    @Transactional(readOnly = true)
    public Predio consultarPredioPorId(String id) {
        Optional<Predio> predioOptional = predioRepository.findById(id);
        return predioOptional.orElse(new Predio());
    }

}
