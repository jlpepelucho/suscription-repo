package ec.com.zurich.suscription.client;

import ec.com.zurich.client.ClientRestConfig;
import ec.com.zurich.suscription.resources.client.ApiClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "EntityStatus", url = "${api.entity.url}", configuration = ClientRestConfig.class)
public interface EntityClient {

    /**
     * TOMAR EN CUENTA QUE LAS CREDENCIALES DEBEN IR CENTRALIZADAS ESTO ES UN DEMO
     * @return response de servicio externo
     */
    @RequestMapping(method = RequestMethod.GET, value = "/healthcheck", produces = "application/json",
            headers = "Authorization=Basic enVyaWNoOnBhc3N3b3JkMjAxOA==")
    ApiClientResponse getStatus();

}
