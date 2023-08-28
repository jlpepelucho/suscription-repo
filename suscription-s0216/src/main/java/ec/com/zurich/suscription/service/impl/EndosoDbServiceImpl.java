package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.library.exceptions.ZurichErrorException;
import ec.com.zurich.suscription.repository.EndosoDbRepository;
import ec.com.zurich.suscription.repository.EndosoEstadoRepository;
import ec.com.zurich.suscription.repository.EstadoDbRepository;
import ec.com.zurich.suscription.resources.entity.Endoso;
import ec.com.zurich.suscription.resources.entity.EndosoEstado;
import ec.com.zurich.suscription.resources.entity.Estado;
import ec.com.zurich.suscription.resources.entity.Proceso;
import ec.com.zurich.suscription.service.EndosoDbService;
import ec.com.zurich.suscription.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndosoDbServiceImpl implements EndosoDbService {

    private final EndosoEstadoRepository endosoEstadoRepository;
    private final EndosoDbRepository endosoDbRepository;
    private final EstadoDbRepository estadoDbRepository;


    @Override
    public void setEndosoEstado(String pEndosoId, String pEstadoId, String pProcesoId,
                                String pUsuarioActualiza) {
        int orden = 1;

        EndosoEstado endosoEstado;
        List<EndosoEstado> endososEstado = getEndosoEstadoByEndosoId(pEndosoId);
        if (endososEstado != null && !endososEstado.isEmpty()) {
            endosoEstado = endososEstado.get(0);
            orden = endosoEstado.getOrden().intValue() + 1;
            endosoEstado.setEsActual(false);
            endosoEstadoRepository.updateEndosoEstadoById(endosoEstado, endosoEstado.getId());


        }

        EndosoEstado endosoEs = new EndosoEstado();
        Proceso proceso;
        proceso = new Proceso();
        Endoso endoso = endosoDbRepository.getEndosoById(pEndosoId).orElseThrow(() -> new ZurichErrorException("002", "Endorsement with ID " + pEndosoId + " not found"));
        Estado estado = estadoDbRepository.getEstadoById(pEstadoId).orElseThrow(() -> new ZurichErrorException("002", "State with ID " + pEstadoId + " not found"));
        proceso.setId(pProcesoId);
        endosoEs.setProcesoId(pProcesoId);
        endosoEs.setEsActual(true);
        endosoEs.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
        endosoEs.setUsuarioActualiza(pUsuarioActualiza);
        endosoEs.setEndoso(endoso);
        endosoEs.setEndosoId(endoso.getId());
        endosoEs.setOrden(new BigDecimal(orden));
        endosoEs.setSistema("S");
        endosoEs.setEstado(estado);
        endosoEs.setEstadoId(estado.getId());
        endosoEstadoRepository.save(endosoEs);

    }

    public List<EndosoEstado> getEndosoEstadoByEndosoId(String pEndosoId) {
        List<EndosoEstado> endosoEstado = null;
        try {
            endosoEstado = endosoEstadoRepository.getEndosoEstadoByEndosoIdAndEsActual(pEndosoId, true).orElse(new ArrayList<>());
        } catch (Exception ignored) {
        }
        return endosoEstado;
    }


}