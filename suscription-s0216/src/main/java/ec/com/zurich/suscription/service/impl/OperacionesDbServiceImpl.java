package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.client.CreationClient;
import ec.com.zurich.suscription.client.SuscriptionDataClient;
import ec.com.zurich.suscription.repository.EndosoAgenteDbRepository;
import ec.com.zurich.suscription.repository.EndosoDiferidoDbRepository;
import ec.com.zurich.suscription.resources.entity.Endoso;
import ec.com.zurich.suscription.resources.entity.EndosoAgente;
import ec.com.zurich.suscription.resources.entity.EndosoDiferido;
import ec.com.zurich.suscription.resources.entity.EndosoItem;
import ec.com.zurich.suscription.resources.mapper.DocumentMapper;
import ec.com.zurich.suscription.resources.model.*;
import ec.com.zurich.suscription.service.OperacionesDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperacionesDbServiceImpl implements OperacionesDbService {
    private final SuscripcionDbServiceImpl suscripcionDbService;
    private final EndosoDiferidoDbServiceImpl endosoDiferidoDbService;
    private final EndosoItemDbServiceImpl endosoItemDbService;
    private final ComisionDbServiceImpl comisionDbService;

    private final CreationClient creationClient;
    private final SuscriptionDataClient suscriptionDataClient;

    private final DocumentMapper documentMapper;

    private final EndosoDiferidoDbRepository endosoDiferidoDbRepository;
    private final EndosoAgenteDbRepository endosoAgenteDbRepository;


    @Override
    public VehicleSuscriptionResponse suscripcionVehiculoResponse(VehicleSuscriptionData request) {

        try {
            return suscripcionVehiculo(request);
        } catch (Exception e) {

            throw new RuntimeException("Error while processing suscripcionVehiculo", e);
        }
    }

    @Override
    public VehicleSuscriptionResponse suscripcionVehiculo(VehicleSuscriptionData suscripcionVehiculo) {
        Endoso endoso = suscripcionDbService.crearPolizaVHMFun(suscripcionVehiculo.endorsement());
        endosoDiferidoDbService.crearNuevoEndosoDiferido(endoso.getId());
        EndosoAgenteObj endosoAgenteObj = new EndosoAgenteObj();
        //endosoAgenteObj.setAgenteId(suscripcionVehiculo.getAgente()); //TODO: poner agente por canal
        String ramoId = endoso.getPoliza().getRamo().getId();
        List<String> agentes;
        if (suscripcionVehiculo.agentIds() != null) {

            agentes = suscripcionVehiculo.agentIds();


            for (String agente : agentes) {
                if (!agente.equals("1")) {
                    List<EndosoAgente> endososAgente = endosoAgenteDbRepository.getEndosoAgenteByEndosoIdInPoliza(suscripcionVehiculo.container(), ramoId, agente).orElse(new ArrayList<>());

                    for (EndosoAgente ea : endososAgente) {
                        endosoAgenteObj.setAgenteId(agente);
                        endosoAgenteObj.setEndosoId(endoso.getId());
                        endosoAgenteObj.setParticipacion(ea.getParticipacion().toString());
                        endosoAgenteObj.setEsLider(ea.getTipoAgente());
                        if (ea.getAcuerdo() != null) {
                            endosoAgenteObj.setAcuerdoId(ea.getAcuerdo().getId());
                        } else {
                            endosoAgenteObj.setAcuerdoId(null);
                        }
                        endosoAgenteObj.setAgenteSinContrato(null);
                        endosoAgenteObj.setParticipacionSinContrato("0");
                        endosoAgenteObj.setUsuarioActualiza(suscripcionVehiculo.endorsement().getUserUpdate());
                        crearEndosoAgenteFuncion(endosoAgenteObj);

                    }
                } else {
                    endosoAgenteObj.setAgenteId("1");
                    endosoAgenteObj.setEndosoId(endoso.getId());
                    endosoAgenteObj.setParticipacion("100");
                    endosoAgenteObj.setEsLider("L");
                    endosoAgenteObj.setAcuerdoId(null);
                    endosoAgenteObj.setAgenteSinContrato(null);
                    endosoAgenteObj.setParticipacionSinContrato("0");
                    endosoAgenteObj.setUsuarioActualiza(suscripcionVehiculo.endorsement().getUserUpdate());
                    crearEndosoAgenteFuncion(endosoAgenteObj);
                }
            }


        } else {
            endosoAgenteObj.setAgenteId("1");
            endosoAgenteObj.setEndosoId(endoso.getId());
            endosoAgenteObj.setParticipacion("100");
            endosoAgenteObj.setEsLider("L");
            endosoAgenteObj.setAcuerdoId(null);
            endosoAgenteObj.setAgenteSinContrato(null);
            endosoAgenteObj.setParticipacionSinContrato("0");
            endosoAgenteObj.setUsuarioActualiza(suscripcionVehiculo.endorsement().getUserUpdate());
            crearEndosoAgenteFuncion(endosoAgenteObj);
        }
        ItemVehicleData vehicleData = suscripcionVehiculo.vehicle();
        EndorsementItem endosoItem = vehicleData.getEndorsementItem();
        endosoItem.setEndorsementId(endoso.getId());
        vehicleData.setEndorsementItem(endosoItem);
        EndosoItem endosoRespuesta = endosoItemDbService.crearItemVehiculoMasivos(vehicleData);


        //Se debe generar el endosoitemcobertura de todos los endosos diferidos cuya fecha desde  sea menor o igual al mes en curso

        List<EndosoDiferido> endososDiferidos = endosoDiferidoDbRepository.getEndosoDiferidoByEndosoId(endoso.getId()).orElse(new ArrayList<>());

        for (EndosoDiferido ed : endososDiferidos) {

            ObjCreacionEndosoItemGrupoCobertura objCreacionEndosoItemGrupoCobertura = new ObjCreacionEndosoItemGrupoCobertura();
//	            objCreacionEndosoItemGrupoCobertura.setEndosoDiferidoId(endosoDiferidoId);
            objCreacionEndosoItemGrupoCobertura.setEndosoDiferidoId(ed.getId());
            objCreacionEndosoItemGrupoCobertura.setUsuarioActualiza(suscripcionVehiculo.endorsement().getUserUpdate());
            objCreacionEndosoItemGrupoCobertura.setFechaActual(OffsetDateTime.now());
            objCreacionEndosoItemGrupoCobertura.setForzarReparto(true);
            objCreacionEndosoItemGrupoCobertura.setEsMasivo(true);

            generarEndososItemsGrupoCobertura(objCreacionEndosoItemGrupoCobertura);

        }

        comisionDbService.calcularComisiones(endoso);


        suscripcionVehiculo.endorsement().setId(endoso.getId());
        DocumentData factura = suscripcionVehiculo.invoice();
        List<String> endosos = new ArrayList<>();
        endosos.add(endoso.getId());
        factura.setEndorsementIds(endosos);


        List<BigDecimal> valorFormaPagoList = new ArrayList<>();
        String endososList = String.join(", ", factura.getEndorsementIds());
        BigDecimal totalFactura = (BigDecimal) suscriptionDataClient.consultarTotalFactura(endososList).data();
        BigDecimal primerValor = totalFactura.divide(new BigDecimal(suscripcionVehiculo.numberPayments()), 2, RoundingMode.HALF_UP);
        BigDecimal restoValor = totalFactura.subtract(primerValor);
        valorFormaPagoList.add(primerValor);
        if (BigDecimal.ZERO.compareTo(restoValor) != 0)
            valorFormaPagoList.add(restoValor);


        factura.setTotalInvoice(totalFactura);
        factura.setPaymentMethods(valorFormaPagoList);
        BillingRequest facturacionRequest = new BillingRequest(documentMapper.toCrearFactura(suscripcionVehiculo.invoice()), null);

        BillingResponse res = (BillingResponse) creationClient.facturarFuncion(facturacionRequest).data();

        VehicleSuscriptionResponse suscripcionVehiculoResponse = new VehicleSuscriptionResponse();
        suscripcionVehiculoResponse.setItemId(endosoItem.getItemId());
        suscripcionVehiculoResponse.setDocumentId(res.getDocumentId());
        suscripcionVehiculoResponse.setDocumentNumber(res.getDocumentNumber());

        return suscripcionVehiculoResponse;
    }

    void crearEndosoAgenteFuncion(EndosoAgenteObj endosoAgenteObj) {
        //Falta servicio
    }

    void generarEndososItemsGrupoCobertura(ObjCreacionEndosoItemGrupoCobertura endosoAgenteObj) {
        //Falta servicio
    }
}
