package ec.com.zurich.suscription.client;


import ec.com.zurich.client.ClientRestConfig;
import ec.com.zurich.suscription.resources.model.BillingRequest;
import ec.com.zurich.web.model.ApiModelResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "CreationClient", url = "${api.billing.creation.url}/function", configuration = ClientRestConfig.class)
public interface CreationClient {
   @PostMapping("/billing")
    ApiModelResponse facturarFuncion(@Valid @RequestBody BillingRequest request);



}
