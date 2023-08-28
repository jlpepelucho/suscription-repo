package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.model.CrearFacturaObj;
import ec.com.zurich.suscription.resources.model.DocumentData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentMapper {

    @Mapping(source = "endorsementIds", target = "endososId")
    @Mapping(source = "debit", target = "debito")
    @Mapping(source = "totalInvoice", target = "totalFactura")
    @Mapping(source = "paymentMethods", target = "valorFormaPago")
    @Mapping(source = "paymentTypes", target = "tipoPago")
    @Mapping(source = "paymentMethodsPercentages", target = "porcentajeFormaPago")
    @Mapping(source = "numberPayments", target = "numeroPagos")
    @Mapping(source = "commisionFreeQuote", target = "cuotaLiberaComision")
    @Mapping(source = "firstPaymentDate", target = "fechasPrimerPago")
    @Mapping(source = "methodPaymentId", target = "formaPagoId")
    @Mapping(source = "paymentPeriods", target = "periodosPagos")
    @Mapping(source = "initialQuote", target = "valorCuotaInicial")
    @Mapping(source = "interest", target = "valorInteres")
    @Mapping(source = "userUpdate", target = "usuarioActualiza")
    @Mapping(source = "documentTypeId", target = "tipoDocumento")
    @Mapping(source = "calculationMethod", target = "formaCalculo")
    @Mapping(source = "manualIssuanceRight", target = "valorDerechoEmisionManual")
    @Mapping(source = "establishmentType", target = "tipoEstablecimiento")
    @Mapping(source = "creditTypeCode", target = "tipoCredito")
    @Mapping(source = "cardNumber", target = "numeroTarjeta")
    @Mapping(source = "deferredQuote", target = "cuotasDiferido")
    @Mapping(source = "dueDate", target = "fechaVencimiento")
    CrearFacturaObj toCrearFactura(DocumentData variablesSistema);



}
