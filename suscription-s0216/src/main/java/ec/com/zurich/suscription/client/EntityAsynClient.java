package ec.com.zurich.suscription.client;

import ec.com.zurich.suscription.resources.client.ApiClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EntityAsynClient {

    @Value("${api.entity.url}")
    private String urlEntity;

    /**
     * TOMAR EN CUENTA QUE LAS CREDENCIALES DEBEN IR CENTRALIZADAS ESTO ES UN DEMO
     * @return response de servicio externo
     */
    public Mono<ApiClientResponse> getStatus() {
        String status = "/healthcheck";
        Mono<ApiClientResponse> productFlux = WebClient.create()
                .get()
                .uri(urlEntity + status)
                .headers(header-> header.setBasicAuth("zurich", "password2018"))
                .retrieve()
                .bodyToMono(ApiClientResponse.class);
        productFlux.subscribe(api -> log.info(api.code()));
        return productFlux;
    }
}
