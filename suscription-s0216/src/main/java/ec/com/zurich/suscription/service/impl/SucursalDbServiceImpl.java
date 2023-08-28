package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.SucursalRepository;
import ec.com.zurich.suscription.resources.entity.Sucursal;
import ec.com.zurich.suscription.service.SucursalDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SucursalDbServiceImpl implements SucursalDbService {

    private final SucursalRepository sucursalRepository;

    @Override
    @Transactional(readOnly = true)
    public Sucursal consultarSucursalPorId(String itemId) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.getSucursalById(itemId);
        return sucursalOptional.orElse(null);
    }
}
