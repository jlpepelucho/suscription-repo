package ec.com.zurich.suscription.resources.client;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiClientResponse(String code, String message, String type, Object data){

}
