package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.CoberturaAsistencia;

public interface CoberturaAsistenciaDbService {

    CoberturaAsistencia consultarCoberturaAsistenciaPorCoberturaId(String coberturaId);

}
