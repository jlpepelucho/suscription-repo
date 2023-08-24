package ec.com.zurich.suscription.controller;

import ec.com.zurich.suscription.client.EntityAsynClient;
import ec.com.zurich.suscription.client.EntityClient;
import ec.com.zurich.suscription.resources.client.ApiClientResponse;
import ec.com.zurich.suscription.resources.mapper.DemoMapper;
import ec.com.zurich.suscription.resources.model.DemoRequest;
import ec.com.zurich.suscription.resources.model.DemoResponse;
import ec.com.zurich.suscription.service.DemoDbService;
import ec.com.zurich.suscription.service.DemoRestService;
import ec.com.zurich.web.model.ApiModelResponse;
import ec.com.zurich.web.model.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Description("Example rest endpoints")
@Tag(name = "rest")
@RequestMapping("/rest")
@Slf4j
@RequiredArgsConstructor
public class DemoController {

    private final DemoDbService demoDbService;
    private final DemoRestService demoRestService;
    protected final DemoMapper mapper;
        private final EntityClient entityClient;
    private final EntityAsynClient entityAsynClient;
    
    @Operation(summary = "Greets a person", description = "A friendly greeting is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))})
    })
    @GetMapping("/hola/{name}")
    public ResponseEntity<ApiModelResponse> sayHello(@Parameter(description = "Person name's") @PathVariable("name") String name) {
        return ApiResponseUtil.ok(demoRestService.sayHello(name));
    }

    @Operation(summary = "System variables", description = "List system variables by filter ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List to search", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))})
    })
    @PostMapping("/variable")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiModelResponse> getVariablesSistema(@Valid @RequestBody DemoRequest request) {
        List<DemoResponse> listResp = mapper.toDemoResponse(demoDbService.getVariableSistema(request));
        return ApiResponseUtil.ok(listResp);
    }

        @Operation(summary = "Synchronous send external service", description = "Get status from another service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))})
    })
    @GetMapping("/entity/status")
    public ResponseEntity<ApiModelResponse> send() {
        log.info("Inicio de consumo sincrono");
        ApiClientResponse client = entityClient.getStatus();
        log.info("Fin de consumo sincrono");
        return ApiResponseUtil.ok(client);
    }

    @Operation(summary = "Asynchronous send external service", description = "Get status from another service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiModelResponse.class))})
    })
    @GetMapping("/entity/async/status")
    public Mono<ResponseEntity<ApiModelResponse>> sendAsyn() {
        log.info("Inicio de consumo asincrono");
        Mono<ApiClientResponse> client = entityAsynClient.getStatus();
        log.info("Fin de consumo asincrono");
        return ApiResponseUtil.okAsync(client);
    }

}
