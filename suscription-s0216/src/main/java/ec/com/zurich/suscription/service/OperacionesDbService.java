package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.model.VehicleSuscriptionData;
import ec.com.zurich.suscription.resources.model.VehicleSuscriptionResponse;



public interface OperacionesDbService {

	/**
	 * Obtiene una lista de todas las variables del sistema que se adapten al filtro
	 * @param request Peticion de busqueda
	 * @return Lista con todos los valores encontrados
	 */
	VehicleSuscriptionResponse suscripcionVehiculoResponse(VehicleSuscriptionData request);
	VehicleSuscriptionResponse suscripcionVehiculo(VehicleSuscriptionData suscripcionVehiculo);

}
