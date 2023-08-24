package ec.com.zurich.suscription.service;

import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.resources.model.DemoRequest;

import java.util.List;

public interface DemoDbService {

	/**
	 * Obtiene una lista de todas las variables del sistema que se adapten al filtro
	 * @param request Peticion de busqueda
	 * @return Lista con todos los valores encontrados
	 */
	List<VariablesSistema> getVariableSistema(DemoRequest request);

}
