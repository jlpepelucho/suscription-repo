package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.repository.BlanketRepository;
import ec.com.zurich.suscription.resources.entity.Blanket;
import ec.com.zurich.suscription.service.BlanketDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlanketDbServiceImpl implements BlanketDbService {

    private final BlanketRepository blanketRepository;

    @Override
    @Transactional(readOnly = true)
    public Blanket consultarBlanketPorItemId(String itemId) {
        Optional<Blanket> blanketOptional = blanketRepository.getBlanketById(itemId);
        return blanketOptional.orElse(null);
    }

}
