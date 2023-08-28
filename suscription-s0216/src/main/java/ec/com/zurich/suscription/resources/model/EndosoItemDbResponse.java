package ec.com.zurich.suscription.resources.model;

import org.springframework.validation.annotation.Validated;

@Validated
public record EndosoItemDbResponse(

        String id,
        String configProductoId

) {}
