package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.TipoItemRepository;
import ec.com.zurich.suscription.resources.entity.TipoItem;
import ec.com.zurich.suscription.service.TipoItemDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TipoItemDbServiceImpl implements TipoItemDbService {

    private final TipoItemRepository tipoItemRepository;

    @Override
    @Transactional(readOnly = true)
    public TipoItem consultarTipoItemPorId(String id) {
        Optional<TipoItem> tipoItem = tipoItemRepository.getTipoItemById(id);
        return tipoItem.orElse(null);

    }
}
