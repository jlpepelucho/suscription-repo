package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.EndosoItemDetalleRepository;
import ec.com.zurich.suscription.service.EndosoItemDetalleDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EndosoItemDetalleDbServiceImpl implements EndosoItemDetalleDbService {

    private final EndosoItemDetalleRepository endosoItemDetalleRepository;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularPrimaDeAM(String endosoItemId) {
        try {
            Optional<BigDecimal> primaDeAm = endosoItemDetalleRepository.consultarEndosoItemDetallePorEndosoItemId(
                    endosoItemId
            );
            return primaDeAm.orElse(BigDecimal.ZERO);
        } catch (Exception e) {
            System.err.println("Could not obtain AM premium: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

}
