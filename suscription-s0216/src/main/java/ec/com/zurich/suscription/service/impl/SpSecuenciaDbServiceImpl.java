package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.repository.SecuencialLogDbRepository;
import ec.com.zurich.suscription.resources.entity.SecuencialLog;
import ec.com.zurich.suscription.service.SpSecuenciaDbService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SpSecuenciaDbServiceImpl implements SpSecuenciaDbService {

    private final SecuencialLogDbRepository secuencialLogDbRepository;


    @Override
    @Transactional()
    public Serializable generarSecuencia(SharedSessionContractImplementor session, String nombreSeq) {
        String sql = "select " + nombreSeq + ".nextval from dual"; // Consulta SQL específica para obtener el siguiente
        // valor
        BigDecimal nextValue = (BigDecimal) session.createNativeQuery(sql).uniqueResult();
        saveLogSecuencial("generarSecuencia:31", nombreSeq, nextValue.toString(), "", sql, null, null, "", "");
        return nextValue.toString();
    }

    @Transactional()
    public void saveLogSecuencial(String metodo, String secuencia, String secuencial, String scriptUpdate,
                                  String script, Boolean esTransaccional, Boolean esGlobal, String valores, String variable) {
        try {
            SecuencialLog secuencialLog = new SecuencialLog();
            secuencialLog.setServicio("suscription");
            secuencialLog.setMetodo(metodo);
            secuencialLog.setSecuencia(secuencia);
            secuencialLog.setSecuencial(secuencial);
            secuencialLog.setScriptUpdate(scriptUpdate);
            secuencialLog.setScript(script);
            secuencialLog.setEsTransaccional(esTransaccional);
            secuencialLog.setEsGlobal(esGlobal);
            secuencialLog.setFechaActualiza(Date.valueOf(LocalDateTime.now().toLocalDate()));
            secuencialLog.setValores(valores);
            secuencialLog.setVariable(variable);
            secuencialLogDbRepository.save(secuencialLog);
        } catch (Exception ignored) {

        }
    }

}
