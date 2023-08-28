package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.library.exceptions.ZurichErrorException;
import ec.com.zurich.suscription.repository.*;
import ec.com.zurich.suscription.resources.entity.*;
import ec.com.zurich.suscription.resources.model.BillingResponse;
import ec.com.zurich.suscription.resources.model.EndorsementSuscription;
import ec.com.zurich.suscription.service.SuscripcionDbService;
import ec.com.zurich.suscription.util.*;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuscripcionDbServiceImpl implements SuscripcionDbService {

    private static final Logger logger = LoggerFactory.getLogger(BillingResponse.class);
    private final EndosoDbRepository endosoDbRepository;
    private final RamoRepository ramoRepository;
    private final ConfiguracionProductoRepository configuracionProductoRepository;
    private final PolizaRepository polizaRepository;
    private final ClienteRepository clienteRepository;
    private final SucursalDbRepository sucursalDbRepository;
    private final VariablesSistemaDbServiceImpl variablesSistemaDbService;
    private final EndosoDbServiceImpl endosoDbService;
    private final TipoItemRepository tipoItemRepository;
    private final EndosoItemDbRepository endosoItemDbRepository;
    private final TransporteAbiertoRepository transporteAbiertoRepository;
    private final HistoricoTransporteAbiertoRepository historicoTransporteAbiertoRepository;
    private final ConvenioPagoRepository convenioPagoRepository;
    private final TipoEndosoRepository tipoEndosoRepository;
    private final DebitoRepository debitoRepository;
    private final UnidadNegocioRepository unidadNegocioRepository;
    private final CoberturaGeneralRepository coberturaGeneralRepository;
    private final VariablesSistemaDbRepository variablesSistemaDbRepository;
    private final CoberturaRepository coberturaRepository;

    public Endoso crearPolizaVHMFun(EndorsementSuscription endoso) {

        Endoso endosoMasivos = this.crearPolizaFun(endoso);
        System.out.println("clienteid mod: " + endoso.getPolicy().clientId());
        endosoMasivos.setValorAsegurado(endoso.getInsuredValue().setScale(2, RoundingMode.HALF_UP));
        endosoMasivos.setValorPrimaNeta(endoso.getNetPremiumValue().setScale(2, RoundingMode.HALF_UP));
        //	endosoMasivos.setClienteId(endoso.getcli);
        endosoDbRepository.updateEndoso(endosoMasivos.getId(), endosoMasivos);

        //Endoso endosoBdd = objectConverter.applyMapping(endosoMasivos, EndosoMapperFunctions.TO_ENDOSO_FROM_ENDOSO_ENTITY);
        return endosoMasivos;

    }

    public Endoso crearPolizaFun(EndorsementSuscription endoso) {
        String usuarioActualiza = endoso.getUserUpdate();

        String id = endoso.getPolicy().id();
        Integer numeroTramite = endoso.getTransactionNumber();
        BigDecimal vigencia = endoso.getPolicy().validityDays();
        Timestamp fechaElaboracion = endoso.getCreateDate();
        if (endoso.getComments() == null || endoso.getComments().isEmpty()) {
            endoso.setComments("");
        }
        String observaciones = endoso.getComments();

        Timestamp vigenciaDesde = endoso.getValidityFrom();
        Timestamp vigenciaHasta = endoso.getValidityTo();
        Ramo ramo = ramoRepository.getRamoById(endoso.getPolicy().branchId()).orElseThrow(() -> new ZurichErrorException("002", "Buquet with ID " + endoso.getPolicy().mainId() + " not found"));


        // String ramoId = null;

        /*
         * if (null != ramoNemotecnico) ramoId = ramoNemotecnico.getId();
         */

        String convenioPagoId = endoso.getPaymentAgreementId();
        if (convenioPagoId == null) {
            convenioPagoId = "-1";
        }

        String clienteId = endoso.getPolicy().clientId();
        String monedaId = endoso.getPolicy().currencyId();
        String sucursalId = endoso.getBranchId();
        String puntoVentaId = endoso.getPointSaleId();
        String firmaDigitalId = endoso.getDigitalSignatureId();
        String clasePolizaId = endoso.getPolicy().policyClassId();
        String tipoItemId = endoso.getItemTypeId();
        String padreId = endoso.getPolicy().parentId();
        String tipoSeguroId = endoso.getPolicy().insuranceTypeId();
        String unidadNegocioId = endoso.getBusinessUnitId();
        String unidadProduccionId = endoso.getPolicy().productionUnitId();
        String vigenciaPolizaId = endoso.getPolicy().validityPolicyId();
        String esAnioDoradoS = endoso.getIsGoldenYear().toString();
        Boolean esAnioDorado = true;
        if (esAnioDoradoS.equals("S")) {
            esAnioDorado = Boolean.TRUE;
        }
        Boolean esPymes = endoso.getPolicy().isPymes();
        BigDecimal porcentajeComisionAgente = endoso.getAgentCommissionPercentage();
        BigDecimal valorComisionAgente = endoso.getAgentCommissionValue();
        BigDecimal tasaBase = endoso.getBaseRate();
        String primaMinima = endoso.getMinimumPremium();
        String limiteIndemnizacion = endoso.getCompensationLimit();
        BigDecimal diasAvisoSiniestro = endoso.getClaimNotificationDays();
        Integer diasConvenioPago = endoso.getPaymentAgreementDays();
        String numeroCertificado = endoso.getCertificateNumber();
        String asociacion = endoso.getAssociation();
        if (limiteIndemnizacion == null) {
            limiteIndemnizacion = "0";
        }
        if (diasAvisoSiniestro == null) {
            diasAvisoSiniestro = new BigDecimal(0);
        }
        if (diasConvenioPago == null) {
            diasConvenioPago = 0;
        }

        Boolean paramExcentoIva = endoso.getIsExemptVat();
        String paramImprimeOriginal = endoso.getPrintsOriginal();
        Boolean esDatoDeOtroSistema = endoso.getIsDataFromAnotherSystem();
        Boolean excentoIva = Boolean.FALSE;
        Boolean datoOtroSistema = Boolean.FALSE;
        if ((esDatoDeOtroSistema != null) && (esDatoDeOtroSistema)) {
            datoOtroSistema = Boolean.TRUE;
        }
        if ((paramExcentoIva != null) && (paramExcentoIva)) {
            excentoIva = Boolean.TRUE;
        }

        Boolean imprimeOriginal = Boolean.FALSE;
        if ((paramImprimeOriginal != null) && paramImprimeOriginal.equals("S"))
            imprimeOriginal = Boolean.TRUE;
        Timestamp fecFinCredito = endoso.getCreditExpirationDate();
        String trayectoAseguradoId = endoso.getPolicy().insuredRouteId();
        String aplicaPagoLiquidacion = endoso.getPolicy().applyPaymentLiquidation();
        String esPago100 = endoso.getPolicy().isPayment100();
        if ("0".equals(aplicaPagoLiquidacion)
                || ("1".equals(aplicaPagoLiquidacion) && null == esPago100 && "S".equals(esPago100))) {
            esPago100 = "S";
        } else {
            esPago100 = "N";
        }

        if (((tipoItemId == null) || (tipoItemId.equals("-1"))) && (ramo != null && ramo.getNombreNemotecnico().equals(RamoNemotecnico.TRANSPORTE))) {
            tipoItemId = getTipoItemIdByClasePolizaId(clasePolizaId);
        }

        String esRenovacion = endoso.getPolicy().isRenewal().toString();
        if (esRenovacion.isEmpty()) {
            esRenovacion = "0";
        }

        String numeroFacturaReferencia = endoso.getPolicy().referenceInvoiceNumber();

        Poliza polizaBdd = new Poliza();
        Endoso endosoBdd = new Endoso();


        if ((id != null) && (!id.equals("-1"))) {
            polizaBdd = buscarPolizaById(polizaBdd.getId());
            try {
                endosoBdd = getEndosoByPolizaIdAndNemotecnico(id, TipoEndosoNemotecnico.POLIZA, Boolean.FALSE);
            } catch (Exception e) {
                // TODO Auto-generated catch block

            }
        }
        polizaBdd.setSinAutorizacionPagos(false);
        polizaBdd.setNumeroItem(new BigDecimal(0));
        polizaBdd.setNumero(new BigDecimal(endoso.getNumber()));
        polizaBdd.setOrden(endoso.getPolicy().order());
        if (!vigencia.toString().isEmpty()) {
            polizaBdd.setVigencia(vigencia);
        }
        polizaBdd.setFechaElaboracion(endoso.getPolicy().createDate().toLocalDateTime());
        polizaBdd.setPrimaAnual(BigDecimal.ZERO);
        polizaBdd.setPrimaTotal(BigDecimal.ZERO);
        double tasa;
        double primaM;
        if (tasaBase == null || tasaBase.toString().isEmpty()) {
            tasa = 0.0;
        } else {
            tasa = tasaBase.doubleValue();
        }
        if (primaMinima == null || primaMinima.isEmpty()) {
            primaM = 0.0;
        } else {
            primaM = Double.parseDouble(primaMinima);
        }
        if (tipoItemId.equals("8")) {
            endosoBdd.setEsDePolizaMadre(true);
            if (endosoBdd.getId() != null)
                try {
                    recalcularTasa(tasa, endoso.getId());
                } catch (Exception ignored) {

                }
        }

        ConfiguracionProducto cp = configuracionProductoRepository.getConfiguracionProductoById(endoso.getPolicy().configProductId()).orElseThrow(() -> new ZurichErrorException("002", "Config Product with ID " + endoso.getPolicy().configProductId() + " not found"));
        polizaBdd.setConfigProductoId(cp.getId());
        polizaBdd.setTasaMinima(new BigDecimal(tasa));
        polizaBdd.setPrimaMinima(new BigDecimal(primaM));
        polizaBdd.setVigenciaDesde(endoso.getValidityFrom().toLocalDateTime());
        polizaBdd.setVigenciaHasta(endoso.getValidityTo().toLocalDateTime());

        if ((id == null) || (id.equals("-1"))) {
            assert ramo != null;
            if (null != ramo.getNombreNemotecnico())
                polizaBdd.setRamo(ramo);
        }

        Moneda monedaPoliza = new Moneda();

        if (null != monedaId) {
            monedaPoliza.setId(monedaId);
            polizaBdd.setMonedaId(monedaPoliza.getId());
        }

        Cliente clientePolizaEndoso = new Cliente();

        if (null != clienteId) {
            clientePolizaEndoso.setId(clienteId);
            Cliente finalClientePolizaEndoso = clientePolizaEndoso;
            clientePolizaEndoso = clienteRepository.getClienteById(clientePolizaEndoso.getId()).orElseThrow(() -> new ZurichErrorException("002", "Client with ID " + finalClientePolizaEndoso.getId() + " not found"));
            polizaBdd.setClienteId(clientePolizaEndoso.getId());
        }

        TipoSeguro tipoSeguroPoliza = new TipoSeguro();

        if (!(tipoSeguroId == null) || tipoSeguroId.equals("") || tipoSeguroId.equals("null")) {
            boolean esCierreMes = getContenidoByNombreVariableSistema("CIERREMES")
                    .equals("1");
            if ((tipoSeguroId.equals("2") || tipoSeguroId.equals("3")) && esCierreMes) {
                throw new RuntimeException(
                        "No se pueden emitir pólizas con coaseguro durante el cierre de mes!");

            }
            tipoSeguroPoliza.setId(tipoSeguroId);
            polizaBdd.setTipoSeguroId(tipoSeguroPoliza.getId());
        } else {
            tipoSeguroPoliza.setId("1");
            polizaBdd.setTipoSeguroId(tipoSeguroPoliza.getId());
        }

        Estado estadoPoliza = new Estado();
        estadoPoliza.setId(PolizaEstadoId.BORRADOR);
        polizaBdd.setEstadoId(estadoPoliza.getId());

        polizaBdd.setTipoContenedor("1");
        polizaBdd.setEsDatoOtroSistema(datoOtroSistema);
        polizaBdd.setEspymes(esPymes);
        polizaBdd.setEsAnioDorado(esAnioDorado);

        Poliza polizaPadre = new Poliza();

        if (null != padreId) {
            polizaPadre.setId(padreId);
            polizaBdd.setPadreId(polizaPadre.getId());
        }

        TrayectoAsegurado trayectoAsegurado = new TrayectoAsegurado();

        if (null != trayectoAseguradoId) {
            trayectoAsegurado.setId(trayectoAseguradoId);
            polizaBdd.setTrayectoaseguradoid(trayectoAsegurado.getId());
        }

        ClasePoliza clasePoliza = new ClasePoliza();

        if (null != clasePolizaId) {
            clasePoliza.setId(clasePolizaId);
            polizaBdd.setClasePolizaId(clasePoliza.getId());
        }

        polizaBdd.setUsuarioActualiza(usuarioActualiza);
        polizaBdd.setFechaActualiza(Util.actualDateTime().toLocalDateTime());

        UnidadProduccion unidadProduccionPoliza = new UnidadProduccion();

        if (null != unidadProduccionId) {
            unidadProduccionPoliza.setId(unidadProduccionId);
            polizaBdd.setUnidadProduccionId(unidadProduccionPoliza.getId());
        }

        if (!esPago100.isEmpty()) {
            polizaBdd.setEsPago100(esPago100);
        }
        if ("N".equals(esPago100) && null != polizaBdd.getAutorizapago100id()) {
            polizaBdd.setAutorizapago100id(null);
        }
        if ((vigenciaPolizaId != null) && (!vigenciaPolizaId.equals("-1"))) {
            VigenciaPoliza vigenciaPoliza = new VigenciaPoliza();
            vigenciaPoliza.setId(vigenciaPolizaId);
            polizaBdd.setVigenciaPolizaId(vigenciaPoliza.getId());
        }
        polizaBdd.setEsrenovacion(esRenovacion);
        polizaBdd.setNumerofacturareferencia(numeroFacturaReferencia);
        int numeroEndoso = 1;
        endosoBdd.setEsAjusteVigencia(Boolean.FALSE);
        endosoBdd.setNumerotramite(numeroTramite != null ? new BigDecimal(numeroTramite) : null);
        endosoBdd.setNumero(new BigDecimal(numeroEndoso));

        TipoItem tipoItemEndoso = new TipoItem();

        if (null != tipoItemId) {
            tipoItemEndoso.setId(tipoItemId);
            endosoBdd.setTipoItem(tipoItemEndoso);
        }

        if (null != clienteId) {
            endosoBdd.setCliente(clientePolizaEndoso);
            endosoBdd.setClienteId(clientePolizaEndoso.getId());
        }

        endosoBdd.setFechaElaboracion(fechaElaboracion.toLocalDateTime());
        float porcentajeComisionAgenteFloat = porcentajeComisionAgente.floatValue();
        endosoBdd.setPorcentajeComisionAgente(new BigDecimal(porcentajeComisionAgenteFloat));
        float valorComisionAgenteFloat = valorComisionAgente.floatValue();
        endosoBdd.setValorComision(new BigDecimal(valorComisionAgenteFloat));
        float limiteIndemnizacionFloat = Float.parseFloat(limiteIndemnizacion);
        endosoBdd.setLimiteindemnizacion(new BigDecimal(limiteIndemnizacionFloat));
        assert ramo != null;
        if (ramo.getNombreNemotecnico().equals(RamoNemotecnico.VI) || ramo.getNombreNemotecnico().equals(RamoNemotecnico.VG) || ramo.getNombreNemotecnico().equals(RamoNemotecnico.AM) || ramo.getNombreNemotecnico().equals(RamoNemotecnico.CV)) {
            endosoBdd.setDiasAvisoSiniestro(new BigDecimal("365"));
        } else {
            if (diasAvisoSiniestro.equals(new BigDecimal("0"))) {
                endosoBdd.setDiasAvisoSiniestro(new BigDecimal("5"));
            } else {
                endosoBdd.setDiasAvisoSiniestro(diasAvisoSiniestro);
            }
        }

        endosoBdd.setDiasConvenioPago(new BigDecimal(diasConvenioPago));
        if (numeroCertificado != null) {
            endosoBdd.setNumerocertificado(numeroCertificado);
        }
        endosoBdd.setValorAsegurado(BigDecimal.ZERO);
        endosoBdd.setValorPrimaNeta(BigDecimal.ZERO);
        endosoBdd.setVigenciaDesde((vigenciaDesde.toLocalDateTime()));
        endosoBdd.setVigenciaHasta((vigenciaHasta.toLocalDateTime()));

        Sucursal sucursalEndoso = new Sucursal();

        if (null != sucursalId) {
            sucursalEndoso.setId(sucursalId);
            endosoBdd.setSucursal(sucursalEndoso);
        }

        //Seteo de numero de poliza
        String nemoramo = ramo.getNombreNemotecnico();
        nemoramo = switch (nemoramo) {
            case RamoNemotecnico.VEHICULOS_PESADOS_MASIVOS, RamoNemotecnico.VEHICULOS_LIVIANOS_MASIVOS, RamoNemotecnico.VEHICULOS_MASIVOS ->
                    RamoNemotecnico.VEHICULOS;
            case RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS, RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS ->
                    RamoNemotecnico.ACCIDENTES_PERSONALES;
            case RamoNemotecnico.RESPONSABILIDAD_CIVIL_MEDICA -> RamoNemotecnico.RESPONSABILIDAD_CIVIL;
            default -> ramo.getNombreNemotecnico();
        };
        Sucursal suc = sucursalDbRepository.getSucursalById(sucursalId).orElseThrow(() -> new ZurichErrorException("002", "Branch with ID " + sucursalId + " not found"));
        polizaBdd.setNumero(new BigDecimal(variablesSistemaDbService.getSecuencial("NROPOL" + nemoramo, suc.getCompaniaSegurosId())));

        PuntoVenta puntoVentaEndoso = new PuntoVenta();

        if (null != puntoVentaId) {
            puntoVentaEndoso.setId(puntoVentaId);
            endosoBdd.setPuntoVenta(puntoVentaEndoso);
        }

        FirmasSucursal firmasSucursalEndoso = new FirmasSucursal();

        if (null != firmaDigitalId) {
            firmasSucursalEndoso.setId(firmaDigitalId);
            endosoBdd.setFirmasucursalid(firmasSucursalEndoso.getId());
        }

//					endosoBdd.setDescripcion(descripcion);
        endosoBdd.setObservaciones(observaciones);
        endosoBdd.setUsuarioActualiza(usuarioActualiza);
        endosoBdd.setFechaActualiza(Util.actualDateTime().toLocalDateTime());

        UnidadNegocio unidadNegocioEndoso = new UnidadNegocio();

        if (null != unidadNegocioId) {
            unidadNegocioEndoso.setId(unidadNegocioId);
            endosoBdd.setUnidadNegocioId(unidadNegocioEndoso.getId());
        }

        TipoSeguro tipoSeguroEndoso = new TipoSeguro();


            tipoSeguroEndoso.setId(tipoSeguroId);
            endosoBdd.setTiposeguroid(tipoSeguroEndoso.getId()); // OT 2005-265
            // F.M.


        endosoBdd.setEsDatoOtroSistema(datoOtroSistema);

        if (!"-1".equals(convenioPagoId)) {
            endosoBdd.setConveniopagoid(convenioPagoId); // Se graba
            // el
            // id
            // del
            // debito
        }

        endosoBdd.setTasaminima(new BigDecimal(tasa));
        if ((asociacion != null) && (!asociacion.isEmpty())) {
            endosoBdd.setAsociacioncb(Boolean.valueOf(asociacion));
        }


        if (getDatosConvenioPagoByDebitoId(convenioPagoId) != null && !getDatosConvenioPagoByDebitoId(convenioPagoId).isEmpty()) {

            throw new RuntimeException("No se puede grabar un convenio asociado con el BANCO INTERNACIONAL");
        }
        TipoEndoso tipoEndoso = getTipoEndosoByNombreNemotecnico(TipoEndosoNemotecnico.POLIZA);
        endosoBdd.setTipoEndoso(tipoEndoso);
        endosoBdd.setExcentoiva(excentoIva);
        endosoBdd.setImprimeoriginal(String.valueOf(imprimeOriginal));
        if (fecFinCredito != null) {
            endosoBdd.setFecfincredito(fecFinCredito);
        }
        if (!convenioPagoId.equals("-1")) {
            String convenio = null;
            List<Debito> debitos = getDebitoByClienteIdAndConvenioPagoId(clienteId,
                    convenioPagoId, Boolean.TRUE);

            for (Debito debito : debitos) {
                convenio = debito.getId();
            }
            if (null != convenio) {
                if (!convenio.equals("-1")) {
                    if (!convenioPagoId.equals(convenio)) {

                        logger.error("El convenio por débito bancario que se esta escogiendo no es exactamente igual al convenio que esta registrado en el Cliente \r\n"
                                + "Se necesita revisar el Cliente si tiene autorización de débito bancario");
                    }
                } else {

                    logger.error("El Cliente no tiene registrado la autorizaci\\u00F3n para d\\u00E9bito bancario: ");

                }
            }
        }
        boolean esMasivo = false;
        try {
            endosoBdd = savePolizaEndosoPol(tasa, polizaBdd, endosoBdd);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la poliza y endoso: " + e.getMessage());
        }

        UnidadNegocio unidadNegocio;
        try {
            unidadNegocio = getUnidadNegocioById(unidadNegocioId);
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la unidad de negocio: " + e.getMessage());
        }

        if (unidadNegocio.getNombre().equals("MASIVOS")) {
            esMasivo = true;
        }

        if ((id == null) || (id.equals("-1"))) {
            try {
                endosoDbService.setEndosoEstado(endosoBdd.getId(), "11", "1", usuarioActualiza);
            } catch (Exception e) {
                throw new RuntimeException("Error al setear el endosoEstado: " + e.getMessage());
            }
            try {
                grabaCoberturaPrincipal(endosoBdd, esMasivo, usuarioActualiza);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                throw new RuntimeException("Error al grabar la cobertura principal: " + e.getMessage());
            }
        }
        return endosoBdd;
    }

    public String getTipoItemIdByClasePolizaId(String pClasePolizaId) {
        List<TipoItem> tipoItem;
        if (pClasePolizaId.equals("4")) {
            tipoItem = tipoItemRepository.getTipoItemsByNombre(UtilService.TRANSPORTE_ESPECIFICO).orElse(new ArrayList<>());
        } else {
            if (pClasePolizaId.equals("1") || pClasePolizaId.equals("2") || pClasePolizaId.equals("3")) {
                tipoItem = tipoItemRepository.getTipoItemsByNombre(UtilService.TRANSPORTE_ABIERTO).orElse(new ArrayList<>());
            } else {
                tipoItem = tipoItemRepository.getTipoItemsByNombre(UtilService.TRANSPORTE_GENERAL).orElse(new ArrayList<>());
            }
        }

        return tipoItem.get(0).getId();
    }

    public Poliza buscarPolizaById(String PolizaId) {
        return polizaRepository.getPolizaById(PolizaId).orElse(null);

    }

    public Endoso getEndosoByPolizaIdAndNemotecnico(String pPolizaId, String pNemotecnico,
                                                    Boolean pAjusteVigencia) {
        Endoso endoso = null;
        try {

            List<Endoso> endosos;
            if (pAjusteVigencia != null) {
                endosos = endosoDbRepository.getEndosoByPolisaIdAndNemotecnicoAndEsAjusteVigencia(pPolizaId, pNemotecnico, pAjusteVigencia).orElse(new ArrayList<>());
            } else {
                endosos = endosoDbRepository.getEndosoByPolisaIdAndNemotecnico(pPolizaId, pNemotecnico).orElse(new ArrayList<>());
            }
            if (!endosos.isEmpty()) {
                endoso = endosos.get(0);
            }
        } catch (NoResultException ignored) {

        } catch (Exception e) {
            logger.error("Error al consultar el endoso por poliza y nemotecnico: " + e.getMessage(), e);
        }
        return endoso;
    }

    public void recalcularTasa(Double pTasa, String pEndosoId) {
        try {
            List<EndosoItem> endososItem = endosoItemDbRepository.getEndosoItemByEndosoId(pEndosoId).orElse(new ArrayList<>());
            for (EndosoItem endosoItem : endososItem) {
                TransporteAbierto transporteAbierto = transporteAbiertoRepository.getTransporteAbiertoById(endosoItem.getItemId()).orElse(new ArrayList<>())
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new ZurichErrorException("002", "Endorsement Item with ID " + endosoItem.getItemId() + " not found"));
                if (null != transporteAbierto) {
                    BigDecimal nuevaTasa = new BigDecimal(pTasa);
                    nuevaTasa = nuevaTasa.add(transporteAbierto.getRecargoObjetoasegurado());
                    nuevaTasa = nuevaTasa.add(transporteAbierto.getRecargoCobertura());
                    nuevaTasa = nuevaTasa.add(transporteAbierto.getRecargoMediotransporte());
                    transporteAbierto.setTasa(nuevaTasa);
                    transporteAbierto.setTasaminima(new BigDecimal(pTasa));
                    transporteAbiertoRepository.updateTransporteAbiertoById(transporteAbierto, transporteAbierto.getId());


                    HistoricoTransporteAbierto historicoTransporteAbierto = historicoTransporteAbiertoRepository.getHistoricoTransporteAbiertoById(endosoItem.getId()).orElseThrow(() -> new ZurichErrorException("002", "Historical Transport Open with ID " + endosoItem.getId() + " not found"));
                    if (null != historicoTransporteAbierto) {
                        historicoTransporteAbierto.setTasa(transporteAbierto.getTasa());
                        historicoTransporteAbierto.setTasaminima(transporteAbierto.getTasaminima());
                        historicoTransporteAbiertoRepository.updateHistoricoTransporteAbiertoById(historicoTransporteAbierto, historicoTransporteAbierto.getId());
                    }

                }

            }
        } catch (Exception e) {
            logger.error("Error al recalcular la Tasa: " + e.getMessage(), e);
        }

    }

    public String getContenidoByNombreVariableSistema(String pNombreVariableSys) {
        List<VariablesSistema> variablesSistema;
        try {
            variablesSistema = getVariablesSistemaByNombre(pNombreVariableSys, Boolean.TRUE);

            int tamano = variablesSistema.size();

            if (tamano == 0) {
                String mensajeError = "La variable de sistema global:" + pNombreVariableSys
                        + ", no esta definida en la base. Comuníquese con el administrador del sistema.";
                logger.error(mensajeError);
                throw new RuntimeException(mensajeError);
            }
            if (tamano > 1) {
                String mensajeError = "La variable de sistema global:" + pNombreVariableSys
                        + ", está devolviendo más de un valor. Comuníquese con el administrador del sistema.";
                logger.error(mensajeError);
                throw new RuntimeException(mensajeError);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar las variables del sistema: ");
        }

        return variablesSistema.get(0).getContenido();
    }

    public String getDatosConvenioPagoByDebitoId(String pDebitoId) {
        List<ConvenioPago> datosConvenioPago;
        try {

            datosConvenioPago = convenioPagoRepository.getConvenioPagoByDebitoId(pDebitoId).orElse(new ArrayList<>());
            if (!datosConvenioPago.isEmpty()) {
                return datosConvenioPago.get(0).getId();
            } else {
                return null;
            }
        } catch (NoResultException nrx) {
            // Ignored
            return null;
        } catch (Exception e) {
            logger.error("Error al consultar el convenioPago: " + e.getMessage(), e);
            return null;
        }

    }

    public TipoEndoso getTipoEndosoByNombreNemotecnico(String pNemotecnico) {
        TipoEndoso tipoEndoso = null;
        List<TipoEndoso> tipoEndosos = tipoEndosoRepository.getTipoEndosoByNemotecnico(pNemotecnico).orElse(new ArrayList<>());
        if (!tipoEndosos.isEmpty()) {
            tipoEndoso = tipoEndosos.get(0);
        }
        return tipoEndoso;
    }

    public List<Debito> getDebitoByClienteIdAndConvenioPagoId(String pClienteId, String pConvenioPagoId,
                                                              Boolean pEstado) {
        List<Debito> debitos = null;
        try {
            if (null != pEstado) {

                debitos = debitoRepository.getDebitoByConvenioPagoIdAndClienteidAndEstado(pConvenioPagoId, pClienteId, pEstado).orElse(new ArrayList<>());
            } else {
                debitos = debitoRepository.getDebitoByConvenioPagoIdAndClienteid(pConvenioPagoId, pClienteId).orElse(new ArrayList<>());
            }

        } catch (NoResultException nrx) {
            // Ignored

        } catch (Exception e) {
            logger.error("Error al consultar el Debito: " + e.getMessage(), e);
        }
        return debitos;
    }

    public Endoso savePolizaEndosoPol(Double pTasa, Poliza pPoliza, Endoso pEndoso) {
        Poliza polizaSaved = new Poliza();
        Endoso endosoSaved = new Endoso();

        boolean esModificacion = Boolean.FALSE;
        if (pPoliza.getId() != null) {
            esModificacion = Boolean.TRUE;
        }
        if (esModificacion == Boolean.TRUE) {
            polizaRepository.updatePolizaById(pPoliza, pPoliza.getId());
        } else {
            polizaSaved = polizaRepository.saveAndFlush(pPoliza);
            System.out.println("Poliza creada: " + polizaSaved.getId());
        }
        pEndoso.setPoliza(polizaSaved);
        String tipoItemId = getTipoItemIdByClasePolizaId(polizaSaved.getClasePolizaId());
        if (("8").equals(tipoItemId) == Boolean.TRUE) {
            pEndoso.setEsDePolizaMadre(true);
            if (pEndoso.getId() != null) {
                this.recalcularTasa(pTasa, pEndoso.getId());
            }
        }
        if (esModificacion == Boolean.TRUE) {
            endosoDbRepository.updateEndoso(pEndoso.getId(), pEndoso);
        } else {
            endosoSaved = endosoDbRepository.saveAndFlush(pEndoso);
            System.out.println("Endoso creado: " + endosoSaved.getId());
        }

        return endosoSaved;

    }


    public UnidadNegocio getUnidadNegocioById(String pId) {
        return unidadNegocioRepository.getUnidadNegocioById(pId).orElse(null);
    }

    public void grabaCoberturaPrincipal(Endoso pEndoso, boolean pEsMasivo, String pUsuarioActualiza) {
        try {
            Poliza poliza = polizaRepository.getPolizaById(pEndoso.getPoliza().getId()).orElseThrow(() -> new ZurichErrorException("002", "Policy with ID " + pEndoso.getPoliza().getId() + " not found"));
            Ramo ramo = ramoRepository.getRamoById(poliza.getRamo().getId()).orElseThrow(() -> new ZurichErrorException("002", "Bouquet with ID " + poliza.getRamo().getId() + " not found"));
            List<Cobertura> coberturas = this.getCoberturaByRamoIdAndEsMasivo(ramo.getId(), pEsMasivo);
            for (Cobertura cobertura : coberturas) {
                if (cobertura.getId().equals(CoberturaId.TODO_RIESGO_VEHICULOS)) { // TODO RIESGO
                    CoberturaGeneral coberturaGeneral = new CoberturaGeneral();
                    coberturaGeneral.setLimitecobertura(new BigDecimal(0));
                    coberturaGeneral.setAfectagrupo(cobertura.getAfectaGrupo());
                    coberturaGeneral.setEndosoid(pEndoso.getId());
                    coberturaGeneral.setAfectavalorasegurado(cobertura.getAfectaValorAsegurado());
                    coberturaGeneral.setAfectaprima(cobertura.getAfectaPrima());
                    coberturaGeneral.setOrden(cobertura.getOrden());
                    coberturaGeneral.setSeccion(cobertura.getSeccion());
                    coberturaGeneral.setTexto(cobertura.getTexto());
                    coberturaGeneral.setLimite(cobertura.getLimite());
                    coberturaGeneral.setCoberturaid(cobertura.getId());
                    coberturaGeneral.setTasa(new BigDecimal(0));
                    coberturaGeneral.setUsuarioactualiza(pUsuarioActualiza);
                    coberturaGeneral.setFechaactualiza(Util.actualDateTime().toLocalDateTime());
                    coberturaGeneralRepository.saveAndFlush(coberturaGeneral);

                }
            }
        } catch (Exception e) {
            logger.error("Error al guardar la Cobertura Principal: " + e.getMessage(), e);
        }

    }

    public List<VariablesSistema> getVariablesSistemaByNombre(String pNombreVariableSys, Boolean esGlobal) {
        List<VariablesSistema> variablesSistema = null;
        try {
            variablesSistema = variablesSistemaDbRepository.getVariablesSistemaByNombreAndEsGlobal(pNombreVariableSys, esGlobal).orElse(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Error al consultar la unidadProduccion: " + e.getMessage(), e);
        }
        return variablesSistema;
    }

    public List<Cobertura> getCoberturaByRamoIdAndEsMasivo(String pRamoId, Boolean pEsMasivo) {
        List<Cobertura> cobertura = new ArrayList<>();
        try {

            cobertura = coberturaRepository.getCoberturaByRamoIdAndAfectaPrimaAndAfectaValorAseguradoAndEsMasivo(pRamoId, pEsMasivo, Boolean.TRUE, Boolean.TRUE).orElse(new ArrayList<>());


        } catch (Exception e) {
            logger.error("Error al consultar la cobertura por el ramoId: " + e.getMessage(), e);
        }
        return cobertura;
    }


}
