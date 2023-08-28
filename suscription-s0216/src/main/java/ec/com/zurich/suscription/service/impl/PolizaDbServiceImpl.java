package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.PolizaRepository;
import ec.com.zurich.suscription.resources.entity.Poliza;
import ec.com.zurich.suscription.resources.model.EndosoItemDbResponse;
import ec.com.zurich.suscription.service.PolizaDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PolizaDbServiceImpl implements PolizaDbService {

    private final PolizaRepository polizaRepository;

    @Override
    @Transactional(readOnly = true)
    public Poliza consultarPolizaPorId(String id) {
        Optional<Poliza> polizaOptional = polizaRepository.findById(id);
        return polizaOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public String consultarProductoIdByContenedor(String contenedor) {
        Optional<String> productoId = polizaRepository.consultarProductoIdByContenedor(contenedor);
        return productoId.orElseThrow(() -> new NotFoundException("Could not find product with container id " + contenedor));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EndosoItemDbResponse> consultarListaItemProducto(String contenedor) {
        return polizaRepository
                .consultarListaItemProducto(contenedor).stream().toList();
    }


}
