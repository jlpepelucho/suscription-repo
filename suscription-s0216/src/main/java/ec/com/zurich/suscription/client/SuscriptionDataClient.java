package ec.com.zurich.suscription.client;


import ec.com.zurich.client.ClientRestConfig;
import ec.com.zurich.web.model.ApiModelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SuscriptionDataClient", url = "${api.suscription.data.url}", configuration = ClientRestConfig.class)
public interface SuscriptionDataClient {
   @PostMapping("/invoicing/total")
    ApiModelResponse consultarTotalFactura(@RequestParam("endorsementsId") String request);



}
