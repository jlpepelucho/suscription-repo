package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.TipoEndosoRepository;
import ec.com.zurich.suscription.resources.entity.TipoEndoso;
import ec.com.zurich.suscription.service.TipoEndosoDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TipoEndosoDbServiceImpl implements TipoEndosoDbService {

    private final TipoEndosoRepository tipoEndosoRepository;

    @Override
    @Transactional(readOnly = true)
    public TipoEndoso consultarTipoEndosoPorId(String id) {
        Optional<TipoEndoso> tipoEndosoOptional = tipoEndosoRepository.getTipoEndosoById(id);
        return tipoEndosoOptional.orElse(null);
    }

}
