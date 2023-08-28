package ec.com.zurich.suscription.service;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

public interface SpSecuenciaDbService {
    Serializable generarSecuencia(SharedSessionContractImplementor session, String nombreSeq);
}
