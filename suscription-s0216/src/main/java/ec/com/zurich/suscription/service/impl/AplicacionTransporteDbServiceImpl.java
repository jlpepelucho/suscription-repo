package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.AplicacionTransporteRepository;
import ec.com.zurich.suscription.service.AplicacionTransporteDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AplicacionTransporteDbServiceImpl implements AplicacionTransporteDbService {

    private final AplicacionTransporteRepository aplicacionTransporteRepository;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal consultarPrimaDeposito(String itemId) {
        try {
            List<BigDecimal> primaDepositoList = aplicacionTransporteRepository.consultarPrimaDeposito(itemId)
                    .stream().toList();
            if (!primaDepositoList.isEmpty()) {
                return primaDepositoList.get(0);
            }
            return BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting deposit bonus by itemId: " + itemId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal consultarTasaCobertura(String itemId) {
        try {
            List<BigDecimal> primaDepositoList = aplicacionTransporteRepository.consultarTasaCobertura(itemId)
                    .stream().toList();
            if (!primaDepositoList.isEmpty()) {
                return primaDepositoList.get(0);
            }
            return BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting coverage rate by itemId: " + itemId);
        }
    }

}
