package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.CoberturaAsistenciaRepository;
import ec.com.zurich.suscription.resources.entity.CoberturaAsistencia;
import ec.com.zurich.suscription.service.CoberturaAsistenciaDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CoberturaAsistenciaDbServiceImpl implements CoberturaAsistenciaDbService {

    private final CoberturaAsistenciaRepository coberturaAsistenciaRepository;

    @Override
    @Transactional(readOnly = true)
    public CoberturaAsistencia consultarCoberturaAsistenciaPorCoberturaId(String coberturaId) {
        List<CoberturaAsistencia> coberturaAsistenciaOptional = coberturaAsistenciaRepository
                .consultarCoberturaAsistenciaPorCoberturaId(coberturaId).stream().toList();
        if (coberturaAsistenciaOptional.isEmpty()) {
            return null;
        }
        return coberturaAsistenciaOptional.get(0);
    }

}
