package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.util.Valor;

public interface VariablesSistemaDbService {

    Integer getSecuencial(String laVariable, String laCompaniaSegurosId, String laSucursalId);

    Integer getSecuencial(String laVariable, String laCompaniaSegurosId);

    Integer recuperaSecuencia(Valor[] valores, String metodo);

    void saveLogSecuencial(String metodo, String secuencia, String secuencial, String scriptUpdate, String script,
                           Boolean esTransaccional, Boolean esGlobal, String valores, String variable);

    String construyeValores(Valor[] valores);

    VariablesSistema getVariable(String laCompaniaSegurosId, String laSucursalId,
                                 String tipoDocumentoId, String puntoEmision);

}
