package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.library.exceptions.ZurichErrorException;
import ec.com.zurich.suscription.repository.*;
import ec.com.zurich.suscription.resources.entity.*;
import ec.com.zurich.suscription.resources.model.BillingResponse;
import ec.com.zurich.suscription.resources.model.RenovacionPoliza;
import ec.com.zurich.suscription.service.CoberturasDbService;
import ec.com.zurich.suscription.util.*;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CoberturasDbServiceImpl implements CoberturasDbService {
    private static final Logger logger = LoggerFactory.getLogger(BillingResponse.class);

    private final TransporteAbiertoDbServiceImpl transporteAbiertoService;
    private final EndosoItemCoberturaRestServiceImpl endosoItemCoberturaRestService;
    private final PredioDbServiceImpl predioService;
    private final TransporteDbServiceImpl transporteService;
    private final BlanketDbServiceImpl blanketDbService;
    private final VehiculoDbServiceImpl vehiculoService;
    private final SucursalDbServiceImpl sucursalService;
    private final PolizaDbServiceImpl polizaService;
    private final TipoEndosoDbServiceImpl tipoEndosoService;
    private final RamoDbServiceImpl ramoService;
    private final TipoItemDbServiceImpl tipoItemService;
    private final VariablesSistemaDbServiceImpl variablesSistemaService;
    private final CoberturaAsistenciaDbServiceImpl coberturaAsistenciaService;
    private final AplicacionTransporteDbServiceImpl aplicacionTransporteService;
    private final EndosoItemDbRepository endosoItemDbRepository;
    private final EndosoItemCoberturaRepository endosoItemCoberturaRepository;


    private final CoberturaRepository coberturaRepository;
    private final EndosoDeducibleRepository endosoDeducibleRepository;
    private final EndosoDbRepository endosoDbRepository;
    private final CoberturaGeneralRepository coberturaGeneralRepository;
    private final EndosoAgenteDbRepository endosoAgenteDbRepository;
    private final TransporteAbiertoRepository transporteAbiertoRepository;
    private final TransporteRepository transporteRepository;
    private final TransporteGeneralRepository transporteGeneralRepository;
    private final BlanketRepository blanketRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EquipoElectronicoRepository equipoElectronicoRepository;
    private final TipoItemRepository tipoItemRepository;
    private final ExtraRepository extraRepository;
    private final RamoDbRepository ramoDbRepository;
    private final CoberturaTextoRepository coberturaTextoRepository;
    private final EndosoItemCoberturaTextoRepository endosoItemCoberturaTextoRepository;
    private final EndosoDeducibleTipoRepository endosoDeducibleTipoRepository;
    private final EndosoItemCoberturaDbRepository endosoItemCoberturaDbRepository;
    private final ComisionAgenteRepository comisionAgenteRepository;
    private final PolizaRepository polizaRepository;
    private final AplicacionTransporteRepository aplicacionTransporteRepository;

    @Transactional()
    public EndosoItemCobertura calculaPrimaCoberturaNeta(
            BigDecimal porcentajeIncendio,
            EndosoItemCobertura endosoItemCobertura,
            Boolean esMasivo,
            Integer mesesGracia,
            RenovacionPoliza renovacionPoliza
    ) {

        if (endosoItemCobertura.getPrimaCoberturaNeta() == null) {
            endosoItemCobertura.setPrimaCoberturaNeta(BigDecimal.ZERO);
        }
        endosoItemCobertura.setEndosoItem(endosoItemDbRepository.getEndosoItemById(endosoItemCobertura.getEndosoItemId()).orElseThrow(() -> new ZurichErrorException("002", "Endorsement Item with id " + endosoItemCobertura.getEndosoItemId() + " not found")));
        endosoItemCobertura.setCobertura(coberturaRepository.findById(endosoItemCobertura.getCoberturaId()).orElseThrow(() -> new ZurichErrorException("002", "Endorsement Item with id " + endosoItemCobertura.getCoberturaId() + " not found")));
        EndosoItem endosoItem = endosoItemCobertura.getEndosoItem();
        String itemId = endosoItemCobertura.getEndosoItem().getItemId();
        Endoso endoso = endosoItemCobertura.getEndosoItem().getEndoso();
        Predio predio = predioService.consultarPredioPorId(itemId);
        TransporteAbierto transporteAbierto = transporteAbiertoService.consultarTransporteAbiertoPorItemId(itemId, true);
        Transporte transporte = transporteService.consultarTransportePorItemId(itemId);
        Blanket blanket = blanketDbService.consultarBlanketPorItemId(itemId);
        Vehiculo vehiculo = vehiculoService.consultarVehiculoPorItemId(itemId);
        Sucursal sucursal = sucursalService.consultarSucursalPorId(endoso.getSucursalId());
        Poliza poliza = polizaService.consultarPolizaPorId(endoso.getPolizaId());
        TipoEndoso tipoEndoso = tipoEndosoService.consultarTipoEndosoPorId(endoso.getTipoEndosoId());
        Cobertura cobertura = consultarCobertura(endosoItemCobertura.getCoberturaId());
        Ramo ramo = ramoService.consultarRamoPorId(
                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamoId()
        );
        TipoItem tipoItem = tipoItemService.consultarTipoItemPorId(
                endosoItemCobertura.getEndosoItem().getTipoItemId()
        );

        String ramoGCNT = ramo.getNombreNemotecnico();
        String tipoEndosoNT = tipoEndoso.getNemotecnico();
        Timestamp vigendiaDesdeTimestamp = Timestamp.valueOf(endoso.getVigenciaDesde());
        Timestamp vigenciaDesde = new Timestamp(vigendiaDesdeTimestamp.getTime());
        BigDecimal diasAnio = variablesSistemaService.consultarCantidadDeVariablesPorVariableyCompaniaDeSegurosId(
                VariablesSistemaNombre.NUMDIASPER,
                false,
                sucursal.getCompaniaSegurosId()
        );
        BigDecimal primaNetaBD = BigDecimal.ZERO;
        Date hasta = Date.valueOf(endoso.getVigenciaHasta().toLocalDate());
        Date desde = Date.valueOf(endoso.getVigenciaDesde().toLocalDate());
        long noDiasL = (hasta.getTime() - desde.getTime()) / MasivosId.DIVISOR_DIAS_LONG.longValue();
        String noDias = Long.toString(noDiasL);
        BigDecimal vigenciaEndoso = new BigDecimal(noDias);
        BigDecimal tasaAnual = endosoItemCobertura.getTasaCobertura();
        String formula = cobertura.getFormulaPrima();

        String tasaAnualString;
        BigDecimal tasaAnualBigDecinmal;
        tasaAnualString = String.valueOf(tasaAnual);
        tasaAnualBigDecinmal = new BigDecimal(tasaAnualString).setScale(4, RoundingMode.HALF_UP);
        String clasePolizaId = poliza.getClasePolizaId();
        if (!clasePolizaId.equals(String.valueOf(ClasePolizaId.A_PLAZO_FIJO))) {
            vigenciaEndoso = diasAnio;
        }
        BigDecimal vigenciaEndosoBigDecinmal = vigenciaEndoso.setScale(2, RoundingMode.HALF_UP);
        BigDecimal diasAnioBigDecimal = diasAnio.setScale(2, RoundingMode.HALF_UP);

        String nemoRamo = ramo.getNombreNemotecnico();
        if (formula != null && !formula.equals(FormulaCobertura.NULL)) {
            if (
                    formula.equals(FormulaCobertura.MON_TAS)
                            || formula.equals(FormulaCobertura.MON_TAS_PFIJA)
                            || formula.equals(FormulaCobertura.MON_TAS_PORCENTAJE)
            ) {
                if (endosoItemCobertura.getEsPrimaFija() != null && !endosoItemCobertura.getEsPrimaFija()) {
                    BigDecimal montoCobertura = BigDecimal.ZERO;
                    if (endosoItemCobertura.getMontoCobertura() != null) {
                        montoCobertura = endosoItemCobertura.getMontoCobertura();
                    }
                    BigDecimal tmp = montoCobertura.multiply(tasaAnualBigDecinmal)
                            .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                    if (!ramoGCNT.equals(RamoNemotecnico.SEGURO_AGRICOLA)) {
                        primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                                .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
                        if (esMasivo) {
                            String nombreCanal = endosoItemCoberturaRestService.obtenerNombreCanalPorPolizaId(
                                    renovacionPoliza.elnumeroPolizaARenovarEn().toString()
                            );
                            if (nombreCanal.equals(MasivosNombre.CANAL_AMAZONAS)) {
                                int mesesCalculoPrima = 12;
                                mesesCalculoPrima = mesesCalculoPrima - mesesGracia;
                                primaNetaBD = tmp.divide(new BigDecimal(mesesCalculoPrima), RoundingMode.HALF_UP);
                                if (noDiasL <= 30) {
                                    long millisSinceEpoch = vigenciaDesde.getTime();
                                    Instant instant = Instant.ofEpochMilli(millisSinceEpoch);
                                    OffsetDateTime dt = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));
                                    Calendar numerodiasFInicioMensual = Calendar.getInstance();
                                    numerodiasFInicioMensual.set(Calendar.YEAR, dt.getYear());
                                    numerodiasFInicioMensual.set(Calendar.MONTH, dt.getMonthValue() - 1);
                                    numerodiasFInicioMensual.set(Calendar.DAY_OF_MONTH, 0);
                                    int numeroDiasMes = numerodiasFInicioMensual.getActualMaximum(Calendar.DAY_OF_MONTH);
                                    primaNetaBD = (primaNetaBD.divide(new BigDecimal(numeroDiasMes), RoundingMode.HALF_UP))
                                            .multiply(new BigDecimal(noDiasL));
                                }
                            } else if (nombreCanal.equals(MasivosNombre.CANAL_ORIGINARSA)) {
                                primaNetaBD =
                                        ((renovacionPoliza.primaNetaOriginarsa()
                                                .divide(MasivosId.IVA, RoundingMode.HALF_UP))
                                                .subtract(MasivosId.VALOR_DERECHOS))
                                                .divide(MasivosId.VALOR_IMPUESTOS, RoundingMode.HALF_UP);
                            }
                        }
                    } else {
                        primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                    }
                } else {
                    if (!tipoEndosoNT.equals(TipoEndosoNemotecnico.EXCLUSION)) {
                        BigDecimal tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                        if (tmp != null) {
                            primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                        }

                    } else {
                        BigDecimal primaCoberturaNeta = endosoItemCobertura.getPrimaCoberturaNeta();
                        int res = primaCoberturaNeta.compareTo(BigDecimal.ZERO);
                        if (res > 0)
                            primaCoberturaNeta = primaCoberturaNeta.multiply(new BigDecimal(-1));

                        BigDecimal tmp = primaCoberturaNeta;
                        primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                    }
                }

                CoberturaAsistencia coberturaAsistencia = coberturaAsistenciaService.consultarCoberturaAsistenciaPorCoberturaId(
                        cobertura.getId()
                );

                if (coberturaAsistencia != null) {
                    BigDecimal valorAsistencia;
                    try {
                        valorAsistencia = coberturaAsistencia.getValorAsistencia();
                        primaNetaBD = valorAsistencia.multiply(vigenciaEndosoBigDecinmal)
                                .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
                        if (primaNetaBD.compareTo(coberturaAsistencia.getValorAsistencia()) > 0)
                            primaNetaBD = valorAsistencia;
                    } catch (Exception ignored) {

                    }
                }
            }

            if (formula.equals(FormulaCobertura.MON_PTA) || formula.equals(FormulaCobertura.MON_TAS_PFIJA)) {
                if (endosoItemCobertura.getEsPrimaFija() != null && !endosoItemCobertura.getEsPrimaFija()) {
                    BigDecimal tmp;
                    tmp = (endosoItemCobertura.getMontoCobertura().multiply(tasaAnualBigDecinmal)
                            .divide(new BigDecimal(1000), 6, RoundingMode.HALF_UP));
                    if (
                            (nemoRamo.equals(RamoNemotecnico.CV)
                                    || nemoRamo.equals(RamoNemotecnico.AM)
                                    || nemoRamo.equals(RamoNemotecnico.VG))
                                    && poliza.getPadreId() != null
                    ) {
                        primaNetaBD = tmp;
                    } else {
                        primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                                .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
                    }
                } else {
                    int resp = endosoItemCobertura.getPrimaCoberturaNeta().compareTo(BigDecimal.ZERO);
                    if (resp != 0) {
                        BigDecimal tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                        primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                    } else {
                        if (endosoItemCobertura.getCobertura().getId().equals(CoberturaId._4000)) {
                            primaNetaBD = endosoItemCoberturaRestService.encuentraPrimaAMporTipoEndoso(
                                    endosoItem,
                                    tipoEndosoNT
                            );
                        }
                    }
                }
            }
            if (formula.equals(FormulaCobertura.LIMEXC_TAS)) {
                String polizaId = poliza.getId();
                BigDecimal limiteActual = consultarValorLimiteActual(
                        polizaId,
                        itemId,
                        endosoItemCobertura.getCoberturaId()
                );
                limiteActual = limiteActual.add(endosoItemCobertura.getLimite());
                BigDecimal limiteCoberturaActual = consultarValorLimiteCoberturaActual(
                        polizaId,
                        itemId,
                        endosoItemCobertura.getCoberturaId()
                );
                limiteCoberturaActual = limiteCoberturaActual.add(endosoItemCobertura.getLimiteCobertura());
                int compResp = limiteActual.compareTo(limiteCoberturaActual);

                if (compResp < 0) {
                    throw new RuntimeErrorException(null,
                            "The limit value of the coverage: " + limiteActual
                                    + " cannot be less than: " + limiteCoberturaActual);
                }

                BigDecimal tmp = (endosoItemCobertura.getLimite()
                        .subtract(endosoItemCobertura.getLimiteCobertura()))
                        .multiply(tasaAnualBigDecinmal)
                        .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                        .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);

            }

            if (formula.equals(FormulaCobertura.LIM_TAS)) {
                BigDecimal tmp = endosoItemCobertura.getLimite()
                        .multiply(tasaAnualBigDecinmal)
                        .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                        .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
            }

            if (formula.equals(FormulaCobertura.LIMEXC_TAS_NOC)) {
                String polizaId = poliza.getId();
                String item2Id = endosoItem.getItemId();
                BigDecimal limiteActual = consultarValorLimiteActual(
                        polizaId,
                        item2Id,
                        endosoItemCobertura.getCobertura().getId()
                );
                limiteActual = limiteActual.add(endosoItemCobertura.getLimite());

                BigDecimal limiteCoberturaActual = consultarValorLimiteCoberturaActual(
                        polizaId,
                        item2Id,
                        endosoItemCobertura.getCobertura().getId()
                );
                limiteCoberturaActual = limiteCoberturaActual.add(endosoItemCobertura.getLimiteCobertura());
                int compResp = limiteActual.compareTo(limiteCoberturaActual);
                if (compResp < 0) {
                    throw new RuntimeErrorException(null,
                            "The limit value of the coverage: " + limiteActual
                                    + " cannot be less than: " + limiteCoberturaActual);
                }
                int numeOcupantes = vehiculo.getNumeroOcupantes();
                BigDecimal numOcupantes = new BigDecimal(numeOcupantes);
                BigDecimal tmp = (numOcupantes.multiply(
                        endosoItemCobertura.getLimite().subtract(endosoItemCobertura.getLimiteCobertura())))
                        .multiply(tasaAnualBigDecinmal)
                        .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                        .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
            }

            if (formula.equals(FormulaCobertura.MON_PTA_PER)) {
                int noPersonas = 1;
                if (Integer.parseInt(tipoItem.getId()) >= 1000) {
                    noPersonas = blanket.getCantidad();
                }
                BigDecimal noPer = new BigDecimal(noPersonas);
                BigDecimal tmp = (endosoItemCobertura.getMontoCobertura().multiply(noPer))
                        .multiply(tasaAnualBigDecinmal)
                        .divide(new BigDecimal(1000), RoundingMode.HALF_UP);
                primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                        .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
            }

            if ((formula.equals(FormulaCobertura.MON_TAS_PER))) {
                if (endosoItemCobertura.getEsPrimaFija() != null && !endosoItemCobertura.getEsPrimaFija()) {
                    int noPersonas = 1;
                    if (Integer.parseInt(tipoItem.getId()) >= 1000) {
                        noPersonas = blanket.getCantidad();
                    }
                    BigDecimal noPer = new BigDecimal(noPersonas);
                    BigDecimal tmp = (endosoItemCobertura.getMontoCobertura().multiply(noPer))
                            .multiply(tasaAnualBigDecinmal)
                            .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                    primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                            .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
                } else {
                    BigDecimal tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                    primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                }
            }

            if (formula.equals(FormulaCobertura.VPD_PORCENTAJE)) {
                BigDecimal tmp = BigDecimal.ZERO;
                String tipoItemId = endosoItem.getTipoItemId();
                BigDecimal tasaCoberturaClase = endosoItemCobertura.getTasaCobertura();
                BigDecimal valorPrimaDeposito = BigDecimal.ZERO;
                if (ramoGCNT.equals(RamoNemotecnico.TRANSPORTE)) {
                    BigDecimal valorPrimaDepositoTR = transporteAbierto != null
                            ? transporteAbierto.getValorprimadeposito()
                            : BigDecimal.ZERO;
                    BigDecimal tasaTR = transporteAbierto != null
                            ? transporteAbierto.getTasa()
                            : BigDecimal.ZERO;
                    if (
                            !tipoItem.getNombre().equals(TipoItemNombre.BLANKET)
                                    || tipoEndosoNT.equals(TipoEndosoNemotecnico.APLICACION)
                    ) {
                        if (
                                clasePolizaId.equals(String.valueOf(ClasePolizaId.A_PLAZO_FIJO))
                                        || clasePolizaId.equals(String.valueOf(ClasePolizaId.INTERNO))
                        ) {
                            if (endosoItemCoberturaRestService.esTipoEndosoValido(tipoEndosoNT)) {
                                int compRes = endosoItemCobertura.getPrimaCoberturaNeta().compareTo(BigDecimal.ZERO);
                                if (compRes != 0) {
                                    tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                                    valorPrimaDeposito = tmp;
                                } else {
                                    valorPrimaDeposito = endosoItem.getValorPrimaIncendio();
                                }
                                tmp = valorPrimaDeposito;
                                endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                            } else {
                                if (
                                        tipoItemId != null
                                                && tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO)
                                                && tipoEndosoNT.equals(TipoEndosoNemotecnico.APLICACION)) {
                                    valorPrimaDeposito = aplicacionTransporteService.consultarPrimaDeposito(
                                            endosoItem.getItemId()
                                    );
                                    BigDecimal valorTasaCobertura = aplicacionTransporteService.consultarTasaCobertura(
                                            endosoItem.getItemId()
                                    );
                                    endosoItemCobertura.setTasaCobertura(valorTasaCobertura);
                                } else {
                                    int respComp = valorPrimaDepositoTR.compareTo(BigDecimal.ZERO);
                                    tasaCoberturaClase = tasaTR;
                                    if (respComp != 0) {
                                        valorPrimaDeposito = valorPrimaDepositoTR;
                                    } else {
                                        int compResp = endosoItemCobertura.getPrimaCoberturaNeta()
                                                .compareTo(BigDecimal.ZERO);
                                        if (compResp != 0) {
                                            tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                                            valorPrimaDeposito = tmp;
                                        } else {
                                            valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                    .multiply(tasaCoberturaClase
                                                            .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                        }
                                    }
                                    endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                }
                                tmp = valorPrimaDeposito;
                            }
                        } else {
                            if (clasePolizaId.equals(String.valueOf(ClasePolizaId.ESPECIFICAS))) {
                                int resComparacion = valorPrimaDepositoTR.compareTo(BigDecimal.ZERO);
                                if (resComparacion != 0) {
                                    int compA = tasaCoberturaClase.compareTo(BigDecimal.ZERO);
                                    int compB = endosoItemCobertura.getPrimaCoberturaNeta()
                                            .compareTo(BigDecimal.ZERO);

                                    if (compA == 0 && compB != 0) {
                                        valorPrimaDeposito = endosoItemCobertura.getPrimaCoberturaNeta();
                                    } else {
                                        tasaCoberturaClase = tasaTR;
                                        valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                .multiply(tasaCoberturaClase
                                                        .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                    }
                                } else {
                                    valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                            .multiply(tasaCoberturaClase
                                                    .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                }
                                tmp = valorPrimaDeposito;
                                endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                            } else {
                                if (endosoItemCoberturaRestService.esTipoEndosoValido(tipoEndosoNT)) {
                                    if (
                                            !tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO)
                                                    && tipoEndosoNT.equals(TipoEndosoNemotecnico.APLICACION)
                                    ) {
                                        endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                    } else {
                                        int resComp = endosoItemCobertura.getTasaCobertura().compareTo(BigDecimal.ZERO);
                                        if (resComp == 0) {
                                            if (
                                                    tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)
                                                            || tipoEndosoNT.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)
                                            ) {
                                                int resComparation = endosoItemCobertura.getPrimaCoberturaNeta()
                                                        .compareTo(BigDecimal.ZERO);
                                                if (resComparation != 0) {
                                                    tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                                                    valorPrimaDeposito = tmp;
                                                } else {
                                                    endosoItemCobertura.setTasaCobertura(endosoItem.getTasaIncendio()
                                                            .multiply(new BigDecimal(-1)));
                                                    valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                            .multiply((endosoItem.getTasaIncendio()
                                                                    .multiply(new BigDecimal(-1))
                                                                    .divide(new BigDecimal(100), RoundingMode.UNNECESSARY)));

                                                }
                                            } else {
                                                int respComp = endosoItemCobertura.getPrimaCoberturaNeta().compareTo(
                                                        BigDecimal.ZERO
                                                );
                                                if (respComp != 0) {
                                                    tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                                                    valorPrimaDeposito = tmp;
                                                } else {
                                                    endosoItemCobertura.setTasaCobertura(endosoItem.getTasaIncendio());
                                                    valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                            .multiply(endosoItem.getTasaIncendio()
                                                                    .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                                }
                                            }
                                        } else {
                                            endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                            valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                    .multiply(tasaCoberturaClase
                                                            .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                        }
                                    }
                                } else {
                                    if (tipoEndosoNT.equals(TipoEndosoNemotecnico.POLIZA)) {
                                        if (
                                                endosoItemCoberturaRestService.esTipoItemIdValido(tipoItemId)
                                                        && poliza.getTipoAceptadoId() != null) {
                                            int compA = tasaCoberturaClase.compareTo(BigDecimal.ZERO);
                                            if (compA == 0) {
                                                BigDecimal valorTasaCobertura = aplicacionTransporteService.consultarTasaCobertura(
                                                        endosoItem.getItemId()
                                                );
                                                endosoItemCobertura.setTasaCobertura(valorTasaCobertura);
                                            } else {
                                                endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                            }
                                        }
                                        endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                        int compB = endosoItemCobertura.getPrimaCoberturaNeta().compareTo(BigDecimal.ZERO);
                                        if (compB != 0) {
                                            tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                                        }
                                        valorPrimaDeposito = tmp;
                                    } else {
                                        if (endoso.getUsuarioActualiza().equals(UsuariosExternosNombre.CARGA_AS_400F)) {
                                            valorPrimaDeposito = endosoItemCobertura.getMontoCobertura()
                                                    .multiply(tasaCoberturaClase
                                                            .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                        } else {
                                            if (
                                                    tipoItemId != null
                                                            && endosoItemCoberturaRestService.esTipoItemIdValido(tipoItemId)
                                                            && tipoEndosoNT.equals(TipoEndosoNemotecnico.APLICACION)
                                            ) {
                                                int compC = tasaCoberturaClase.compareTo(BigDecimal.ZERO);
                                                if (compC == 0) {
                                                    BigDecimal tasaCobertura = aplicacionTransporteService.consultarTasaCobertura(
                                                            endosoItem.getItemId()
                                                    );
                                                    endosoItemCobertura.setTasaCobertura(tasaCobertura);
                                                    valorPrimaDeposito = aplicacionTransporteService.consultarPrimaDeposito(
                                                            endosoItem.getItemId()
                                                    );
                                                } else {
                                                    endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                                    BigDecimal n = ((endosoItemCobertura.getMontoCobertura()
                                                            .multiply(tasaCoberturaClase))
                                                            .divide(new BigDecimal(100), RoundingMode.UNNECESSARY))
                                                            .multiply(new BigDecimal(100));
                                                    double a = n.doubleValue();
                                                    valorPrimaDeposito = BigDecimal.valueOf(Math.rint(a) / 100);

                                                }
                                            } else {
                                                if (
                                                        tipoItemId != null
                                                                && tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO)
                                                                && tipoEndosoNT.equals(TipoEndosoNemotecnico.INCLUSION)
                                                ) {
                                                    BigDecimal tasaCobertura = aplicacionTransporteService.consultarTasaCobertura(
                                                            endosoItem.getItemId()
                                                    );
                                                    endosoItemCobertura.setTasaCobertura(tasaCobertura);
                                                } else if (
                                                        tipoItemId != null
                                                                && tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO)
                                                                && tipoEndosoNT.equals(TipoEndosoNemotecnico.EXCLUSION)
                                                ) {
                                                    int compD = tasaCoberturaClase.compareTo(BigDecimal.ZERO);
                                                    if (compD == 0) {
                                                        BigDecimal tasaCobertura = aplicacionTransporteService.consultarTasaCobertura(
                                                                endosoItem.getItemId()
                                                        );
                                                        endosoItemCobertura.setTasaCobertura(tasaCobertura);
                                                        valorPrimaDeposito = aplicacionTransporteService.consultarPrimaDeposito(
                                                                endosoItem.getItemId()).multiply(new BigDecimal(-1));
                                                    } else {
                                                        endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                                                        valorPrimaDeposito = endosoItemCobertura
                                                                .getMontoCobertura()
                                                                .multiply(tasaCoberturaClase
                                                                        .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                                    }
                                                } else if (
                                                        tipoItemId != null
                                                                && !tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO)
                                                                && tipoEndosoNT.equals(TipoEndosoNemotecnico.EXCLUSION)
                                                ) {
                                                    valorPrimaDeposito = transporte != null
                                                            ? transporte.getValorPrimaDeposito().multiply(new BigDecimal(-1))
                                                            : BigDecimal.ZERO;
                                                    endosoItemCobertura.setTasaCobertura(
                                                            transporte != null
                                                                    ? transporte.getTasa()
                                                                    : BigDecimal.ZERO
                                                    );
                                                } else {
                                                    endosoItemCobertura.setTasaCobertura(
                                                            transporte != null
                                                                    ? transporte.getTasa()
                                                                    : BigDecimal.ZERO
                                                    );
                                                    valorPrimaDeposito = endosoItem.getValorAsegurado()
                                                            .multiply(endosoItemCobertura.getTasaCobertura()
                                                                    .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                                                }
                                            }
                                        }
                                    }
                                }
                                tmp = valorPrimaDeposito;
                            }
                        }
                    } else {
                        if (endosoItemCobertura.getEsPrimaFija() != null && !endosoItemCobertura.getEsPrimaFija()) {
                            tmp = endosoItemCobertura.getMontoCobertura().multiply(tasaAnualBigDecinmal)
                                    .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                        } else {
                            tmp = endosoItemCobertura.getPrimaCoberturaNeta();
                        }
                    }
                    primaNetaBD = tmp;
                    if (
                            tipoItemId != null
                                    && tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO) && endoso.getEsDePolizaMadre()
                    ) {
                        if (
                                (clasePolizaId.equals(String.valueOf(ClasePolizaId.A_PLAZO_FIJO))
                                        || clasePolizaId.equals(String.valueOf(ClasePolizaId.ABIERTAS)))
                        ) {
                            if (endosoItem.getNumeroItem() > 1) {
                                if (tipoEndosoNT.equals(TipoEndosoNemotecnico.POLIZA)) {
                                    primaNetaBD = BigDecimal.ZERO;
                                } else {
                                    if (endoso.getClienteId().equals(poliza.getClienteId())) {
                                        primaNetaBD = BigDecimal.ZERO;
                                    }
                                }
                            } else {
                                if (
                                        !tipoEndosoNT.equals(TipoEndosoNemotecnico.POLIZA)
                                                && !tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)
                                                && !tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO)
                                ) {
                                    if (endoso.getClienteId().equals(poliza.getClienteId())) {
                                        primaNetaBD = BigDecimal.ZERO;
                                    }
                                }
                            }
                        }
                    }
                } else if (ramoGCNT.equals(RamoNemotecnico.INCENDIO)) {
                    if (
                            tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO)
                                    || tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)
                                    || tipoEndosoNT.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO)
                                    || tipoEndosoNT.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)
                    ) {
                        valorPrimaDeposito = endosoItem.getValorPrimaIncendio();
                        tasaCoberturaClase = ((porcentajeIncendio.multiply(endosoItem.getTasaIncendio()))
                                .divide(new BigDecimal(100), RoundingMode.UNNECESSARY))
                                .setScale(3, RoundingMode.UNNECESSARY);
                    } else {
                        valorPrimaDeposito = predio.getValorPrimaDeposito();
                        tasaCoberturaClase = ((porcentajeIncendio.multiply(predio.getTasa()))
                                .divide(new BigDecimal(100), RoundingMode.HALF_UP))
                                .setScale(3, RoundingMode.HALF_UP);
                    }
                    endosoItemCobertura.setTasaCobertura(tasaCoberturaClase);
                    tmp = valorPrimaDeposito
                            .multiply(porcentajeIncendio
                                    .divide(new BigDecimal(100), RoundingMode.UNNECESSARY));
                    primaNetaBD = tmp.setScale(2, RoundingMode.HALF_UP);
                } else {
                    tmp = endosoItemCobertura.getMontoCobertura()
                            .multiply(tasaAnualBigDecinmal)
                            .divide(new BigDecimal(100), RoundingMode.HALF_UP);
                    primaNetaBD = tmp.multiply(vigenciaEndosoBigDecinmal)
                            .divide(diasAnioBigDecimal, RoundingMode.HALF_UP);
                }
            }
        }
        endosoItemCobertura.setPrimaCoberturaNeta(primaNetaBD);
        return endosoItemCobertura;
    }

    public void eliminaCoberturasItem(EndosoItem endosoItem) {

        try {
            List<EndosoItemCobertura> result = endosoItemCoberturaRepository.getEndosoItemCoberturaByEndosoItemId(endosoItem.getId()).orElse(new ArrayList<>());

            for (EndosoItemCobertura endosoItemCobertura : result) {
                eliminaDeducibleCobertura(endosoItemCobertura);
                eliminaEndosoItemCoberturaTexto(endosoItemCobertura);
                endosoItemCoberturaRepository.deleteEndosoItemCoberturaById(endosoItemCobertura.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar EndosoItemCobertura. ", e);
        }

    }

    public void eliminaDeducibleItem(EndosoItem endosoItem) {

        try {
            List<EndosoDeducible> result = endosoDeducibleRepository.getEndosoDeducibleByEndosoidAndCriterioDeducibleIdAndEndosoItemId(endosoItem.getEndoso().getId(), CriterioDeducibleId.ESPECIFICO_ITEM, endosoItem.getId()).orElse(new ArrayList<>());

            for (EndosoDeducible endosoDeducible : result) {
                borraDeducibleTipo(endosoDeducible);
                endosoDeducibleRepository.deleteEndosoDeducibleById(endosoDeducible.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar EndosoDeducible. ", e);
        }


    }
@Transactional()
    public void grabaCoberturasGenerales(String usuarioActualiza, EndosoItem endosoItem) {

        String endosoId = endosoItem.getEndoso().getId();
        int count = 0;
        if (endosoItem.getTipoItemId() != null && !endosoItem.getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO)
                && !endosoItem.getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.APLICACION)) {

            try (Stream<List<CoberturaGeneral>> ignored = coberturaGeneralRepository.getCoberturaGeneralByEndosoid(endosoId).stream()) {
                List<CoberturaGeneral> result = coberturaGeneralRepository.getCoberturaGeneralByEndosoid(endosoId).orElse(new ArrayList<>());
                for (CoberturaGeneral coberturaGeneral : result) {
                    //por cada registro de cobertura General grabo uno en EndosoItemCobertura
                    EndosoItemCobertura endosoItemCobertura = new EndosoItemCobertura();
                    endosoItemCobertura.setAfectaGrupo(coberturaGeneral.getAfectagrupo());
                    endosoItemCobertura.setAfectaValorAsegurado(coberturaGeneral.getAfectavalorasegurado());
                    endosoItemCobertura.setAfectaPrima(coberturaGeneral.getAfectaprima());
                    endosoItemCobertura.setEsTodoRiesgo(coberturaGeneral.getEstodoriesgo());
                    endosoItemCobertura.setOrden(coberturaGeneral.getOrden());
                    endosoItemCobertura.setSeccion(coberturaGeneral.getSeccion());
                    endosoItemCobertura.setTexto(coberturaGeneral.getTexto());
                    endosoItemCobertura.setLimite(coberturaGeneral.getLimite());
                    endosoItemCobertura.setLimiteCobertura(coberturaGeneral.getLimitecobertura());
                    endosoItemCobertura.setEndosoItem(endosoItem);
//                        endosoItemCobertura.setCobertura(new Cobertura());
//                        endosoItemCobertura.getCobertura().setId(coberturaGeneral.getCobertura().getId());
                    endosoItemCobertura.setCoberturaId(coberturaGeneral.getCoberturaid());
                    endosoItemCobertura.setTasaCobertura(coberturaGeneral.getTasa());
                    endosoItemCobertura.setCoberturaGeneralId(coberturaGeneral.getId());
                    endosoItemCobertura.setMontoCobertura(calcularMonto(endosoItemCobertura));
                    endosoItemCobertura.setUsuarioActualiza(usuarioActualiza);
                    endosoItemCobertura.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
                    //SETEO EL PORCENTAJE DE COMISION NORMAL DEL AGENTE SEGUN CONTRATO
                    EndosoAgente agenteLiderId = getEndosoAgenteByEndosoIdAndTipoAgente(
                            endosoItem.getEndoso().getId(), TipoAgente.LIDER);

                    BigDecimal porcentajeComisionNormal = porcentajeComisionEIC(agenteLiderId != null ? agenteLiderId.getId() : null,
                            coberturaGeneral.getCoberturaid());
                    endosoItemCobertura.setPorcentajeComisionNormal(porcentajeComisionNormal);
                    if (coberturaGeneral.getAfectaprima()) {
                        count = count + 1;
                        calculaPrimaCoberturaNeta(BigDecimal.ZERO, endosoItemCobertura, false, BigDecimal.ZERO.intValue(), null);
                    }
                    // Valores obligatorios por defecto a setearse
                    endosoItemCobertura.setAplicaDeducible(false);
                    endosoItemCobertura = endosoItemCoberturaRepository.saveAndFlush(endosoItemCobertura);
                    String idTemp = endosoItemCobertura.getId();
                    //endosoItemCobertura.setId(idTemp);F.M.M.
                    /// endosoItemCobertura.retrieve();
                    copiaCoberturaTexto(usuarioActualiza, endosoItemCobertura);
                    grabaDeducibles(idTemp, CriterioDeducibleId.COBERTURA_GENERAL, idTemp,
                            endosoId, endosoItemCobertura.getCobertura().getId(), usuarioActualiza);
                }
            }
        }
        if (count == 0) {
            grabaCoberturasGeneralesTransporte(usuarioActualiza, endosoItem, false);
        }

    }

    public EndosoAgente getEndosoAgenteByEndosoIdAndTipoAgente(
            String endosoId, String tipoAgente) {

        EndosoAgente endosoAgente = null;
        try {
            List<EndosoAgente> endosos ;
            endosos = endosoAgenteDbRepository.getEndosoAgenteByEndosoIdAndTipoAgente(endosoId, tipoAgente).orElse(new ArrayList<>());
            if (!endosos.isEmpty())
                endosoAgente = endosos.get(0);
        } catch (Exception e) {
            logger.error("Error al obtener getEndosoAgenteByEndosoIdAndTipoAgente : " + e.getMessage(), e);
        }
        return endosoAgente;

    }

    public BigDecimal getPorcentajeComisionEIC(String pAgenteId, String pCoberturaId) {
        return porcentajeComisionEIC(pAgenteId, pCoberturaId);
    }

    public BigDecimal calcularMonto(EndosoItemCobertura endosoItemCobertura) {

        if (endosoItemCobertura.getLimiteCobertura() == null) {
            endosoItemCobertura.setLimiteCobertura(BigDecimal.ZERO);
        }

        BigDecimal elMontoCobertura = BigDecimal.ZERO;
        Integer numeroOcupantes = 1;
        BigDecimal valorExtrasVehiculo = BigDecimal.ZERO;

        Optional<TransporteAbierto> transporteAbiertoOp ;
        TransporteAbierto transporteAbierto = null;
        Optional<Transporte> transporteOP ;
        Transporte transporte = null;
        Optional<TransporteGeneral> transporteGeneralOp;
        TransporteGeneral transporteGeneral = null;
        Optional<Blanket> blanketOp ;
        Blanket blanket = null;
        Optional<Vehiculo> vehiculoOp ;
        Vehiculo vehiculo = null;
        Optional<EquipoElectronico> equipoOp ;
        EquipoElectronico equipoElectronico = null;
        transporteAbiertoOp = transporteAbiertoRepository.getTransporteAbiertoById(endosoItemCobertura.getEndosoItem().getItemId()).orElse(new ArrayList<>())
                .stream()
                .findFirst();
        if (transporteAbiertoOp.isPresent()) {
            transporteAbierto = transporteAbiertoOp.get();
        }
        transporteOP = transporteRepository.getTransporteById(endosoItemCobertura.getEndosoItem().getItemId());
        if (transporteOP.isPresent()) {
            transporte = transporteOP.get();
        }
        transporteGeneralOp = transporteGeneralRepository.getTransporteGeneralsById(endosoItemCobertura.getEndosoItem().getItemId());
        if (transporteGeneralOp.isPresent()) {
            transporteGeneral = transporteGeneralOp.get();
        }
        blanketOp = blanketRepository.getBlanketById(endosoItemCobertura.getEndosoItem().getItemId());
        if (blanketOp.isPresent()) {
            blanket = blanketOp.get();
        }
        vehiculoOp = vehiculoRepository.getVehiculoById(endosoItemCobertura.getEndosoItem().getItemId());
        if (vehiculoOp.isPresent()) {
            vehiculo = vehiculoOp.get();
        }
        equipoOp = equipoElectronicoRepository.getEquipoElectronicoById(endosoItemCobertura.getEndosoItem().getItemId());
        if (equipoOp.isPresent()) {
            equipoElectronico = equipoOp.get();
        }

        Optional<Endoso> endosoOp = endosoDbRepository.getEndosoById(endosoItemCobertura.getEndosoItem().getEndoso().getId());
        Endoso endoso =new Endoso();
        if(endosoOp.isPresent()) {
            endoso = endosoOp.get();
        }
        String nemoTipoEndoso = endoso.getTipoEndoso().getNemotecnico();
        String formulaCobertura = endosoItemCobertura.getCobertura().getFormulaMontoAsegurado();

        if (formulaCobertura != null && formulaCobertura.equals(FormulaCobertura.ASE) && endoso.getPoliza().getEsDatoOtroSistema())
            formulaCobertura = FormulaCobertura.ASE_VF;
        String tipoItem = tipoItemRepository
                .getTipoItemById(endosoItemCobertura.getEndosoItem().getTipoItemId())
                .map(TipoItem::getNombre)
                .orElse("");

        if (formulaCobertura != null) {
            if (tipoItem.equals(TipoItemNombre.VEHICULO)) {
                numeroOcupantes = vehiculo != null ? vehiculo.getNumeroOcupantes() : null;
                if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                    valorExtrasVehiculo = endosoItemCobertura.getEndosoItem().getVal1();
                else {
                    BigDecimal valorAseguradoVehiculoExtras = BigDecimal.ZERO;
                    List<Extra> extrasLista = extraRepository.getExtraByVehiculoidAndEstaincluido(vehiculo.getId(), true).orElse(new ArrayList<>());
                    for (Extra extras : extrasLista) {
                        if (extras.getId() != null) {
                            valorAseguradoVehiculoExtras = valorAseguradoVehiculoExtras.add(extras.getValorasegurado());
                        }
                    }
                    valorExtrasVehiculo = valorAseguradoVehiculoExtras;
                }
            }
            if (formulaCobertura.equals(FormulaCobertura.LIM)) {
                if ((endosoItemCobertura.getLimiteCobertura().compareTo(BigDecimal.ZERO) != 0) && (nemoTipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)) && (
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES)
                                || endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VI) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG))) {
                    if (endosoItemCobertura.getEndosoItem().getEndoso().getTipoItem().getEsGrupal()) {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(new BigDecimal(blanket.getCantidad()));

                    } else {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura();
                    }
                } else {
                    if (endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG)
                            || endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM)) {
                        if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)) {
                            if (endosoItemCobertura.getEndosoItem().getCantidad() != 0) {
                                elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(
                                        new BigDecimal(endosoItemCobertura.getEndosoItem().getCantidad()));
                            } else {

                                elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(
                                        cantidadActualdelGrupo(endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId(), endosoItemCobertura.getEndosoItem().getItemId()));
                            }
                        } else if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)) {
                            endosoItemCobertura.setLimiteCobertura(getValorLimiteCoberturaActualVG(endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId(),
                                    endosoItemCobertura.getEndosoItem().getItemId(), endosoItemCobertura.getCobertura().getId()));
                            elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(cantidadActualdelGrupo(
                                    endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId(), endosoItemCobertura.getEndosoItem().getItemId()));

                        }
                    } else {
                        elMontoCobertura = endosoItemCobertura.getLimite();
                    }
                }
            }
            if (formulaCobertura.equals(FormulaCobertura.LIM_NOC)) {
                elMontoCobertura = endosoItemCobertura.getLimite().multiply(new BigDecimal(numeroOcupantes));
            }
            if (formulaCobertura.equals(FormulaCobertura.ASE_EXT)) {
                if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getVal1();
                else {
                    if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION))
                        elMontoCobertura = valorExtrasVehiculo.multiply(new BigDecimal(-1));
                    else
                        elMontoCobertura = valorExtrasVehiculo;
                }
            }
            if (formulaCobertura.equals(FormulaCobertura.ASE)) {
                if ((endosoItemCobertura.getLimiteCobertura().compareTo(BigDecimal.ZERO) != 0) && (nemoTipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) && (
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VI) ||
                                endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG))) {
                    if (endosoItemCobertura.getEndosoItem().getEndoso().getTipoItem().getEsGrupal()) {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(new BigDecimal(blanket.getCantidad()));

                    } else {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura();
                    }
                } else if (!endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.TRANSPORTE)) {
                    if (endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG) ||
                            endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM)) {
                        if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)) {
                            if (endosoItemCobertura.getEndosoItem().getCantidad() != 0) {
                                elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(new BigDecimal(endosoItemCobertura.getEndosoItem().getCantidad()));
                            } else {

                                elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(cantidadActualdelGrupo(endosoItemCobertura.getEndosoItem()
                                        .getEndoso().getPoliza().getId(), endosoItemCobertura.getEndosoItem().getItemId()));
                            }

                        } else if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)) {
                            endosoItemCobertura.setLimiteCobertura(getValorLimiteCoberturaActualVG(endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId(),
                                    endosoItemCobertura.getEndosoItem().getItemId(), endosoItemCobertura.getCobertura().getId()));
                            elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(cantidadActualdelGrupo(
                                    endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getId(), endosoItemCobertura.getEndosoItem().getItemId()));

                        }
                    } else {
                        //M.A.T.F  OT - 200700609 Calcula valor asegurado Riesgos Especiales y Robo

                        String ramoNM = ramoDbRepository.getRamoById(endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getRamo().getId())
                                .map(ramo -> ramo.get(0).getNombreNemotecnico())
                                .orElse(""); //endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getRamo().getNombreNemotecnico();
                        if (ramoNM.equals(RamoNemotecnico.RIESGOS_ESPECIALES) || ramoNM.equals(RamoNemotecnico.ROBO_ASALTO)) {
                            if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                            else
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorAsegurado();
                        } else {
                            if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION) && (ramoNM.equals(RamoNemotecnico.VEHICULOS)
                                    || ramoNM.equals(RamoNemotecnico.VEHICULOS_LIVIANOS_MASIVOS) || ramoNM.equals(RamoNemotecnico.VEHICULOS_PESADOS_MASIVOS)
                                    || ramoNM.equals(RamoNemotecnico.VEHICULOS_MASIVOS))) {
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem().add(valorExtrasVehiculo);
                            } else {
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                            }
                        }
                    }
                } else {
                    if (!tipoItem.equals(TipoItemNombre.BLANKET)) {
                        if (endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getClasePolizaId().equals("1")) {
                            if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO) ||
                                    nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)) //ajuste de valorAsegurado en aumento o rebaja
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                            else if (endosoItemCobertura.getEndosoItem().getTipoItemId() != null && endosoItemCobertura.getEndosoItem().getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO)
                                    && (endosoItemCobertura.getEndosoItem().getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.APLICACION) ||
                                    endosoItemCobertura.getEndosoItem().getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.POLIZA))) {//EMISIÓN TRANSPORTE 04/05/2015 VHBV
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorAsegurado();
                            } else {
                                	/*if (null != getEndosoItem().getTransporte(pm).getFormaCalculo() && getEndosoItem().getTransporte(pm).getFormaCalculo().equals("B")) {
                                	if (null != getEndosoItem().getTransporte(pm).getValorLimiteEmbarque()) {
                                		elMontoCobertura = getEndosoItem().getTransporte(pm).getValorLimiteEmbarque();
                                    }
                                } else {*/
                                if (null != transporteAbierto && null != transporteAbierto.getLimitemaximoembarque()) {
                                    elMontoCobertura = transporteAbierto.getLimitemaximoembarque();
                                }
                                //}
                            }
                        } else {
                            if (!nemoTipoEndoso.equals(TipoEndosoNemotecnico.APLICACION)) {
                                if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)
                                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)
                                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)) //ajuste de valorAsegurado en aumento o rebaja
                                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                                else if (endosoItemCobertura.getEndosoItem().getTipoItemId() != null && endosoItemCobertura.getEndosoItem()
                                        .getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO))
                                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                                else {
                                    if (endosoItemCobertura.getEndosoItem().getTipoItemId().equals(TipoItemId.TRANSPORTE_ESPECIFICO))
                                        elMontoCobertura = transporte.getValorLimiteEmbarque();
                                    else if (endosoItemCobertura.getEndosoItem().getTipoItemId().equals(TipoItemId.TRANSPORTE_GENERAL))
                                        elMontoCobertura = transporteGeneral.getLimiteporembarque();
                                }


                            } else if (endosoItemCobertura.getEndosoItem().getTipoItemId() != null && endosoItemCobertura.getEndosoItem()
                                    .getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO)) {
                                elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorAsegurado();
                            } else {
                                elMontoCobertura = transporte.getValorLimiteEmbarque();
                            }
                        }
                    } else {
                        elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                    }
                }
            }
            if (formulaCobertura.equals(FormulaCobertura.ASE_PRG_VF)) {
                if (endosoItemCobertura.getId() != null && !endosoItemCobertura.getId().equals("-1")) {
                    if (!tipoItem.equals(TipoItemNombre.BLANKET)) {
                        if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                            elMontoCobertura = endosoItemCobertura.getEndosoItem().getVal3();
                        else
                            elMontoCobertura = equipoElectronico.getValortotalportadores();
                    } else {
                        if (endosoItemCobertura.getEndosoItem().getValorItem() != endosoItemCobertura.getMontoCobertura())
                            elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                        else
                            elMontoCobertura = blanket.getValorTotalPortadores();
                    }
                } else
                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
            }
            if (formulaCobertura.equals(FormulaCobertura.ASE_MNT_VF)) {
                if (endosoItemCobertura.getId() != null && !endosoItemCobertura.getId().equals("-1")) {
                    if (!tipoItem.equals(TipoItemNombre.BLANKET)) {
                        if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                            elMontoCobertura = (endosoItemCobertura.getEndosoItem().getVal1().add(endosoItemCobertura.getEndosoItem().getVal2()));
                        else
                            elMontoCobertura = equipoElectronico.getValortotalincremento();
                    } else {
                        if (endosoItemCobertura.getEndosoItem().getValorItem() != endosoItemCobertura.getMontoCobertura())
                            elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                        else
                            elMontoCobertura = blanket != null ? blanket.getValorTotalIncremento() : null;
                    }
                } else
                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
            }
            if (formulaCobertura.equals(FormulaCobertura.ASE_VF)) {
                if ((endosoItemCobertura.getLimiteCobertura().compareTo(BigDecimal.ZERO) != 0) && (nemoTipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)
                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)
                        || nemoTipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION))
                        && (endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES) ||
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS) ||
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS) ||
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VI) ||
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM) ||
                        endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG))) {
                    if (endosoItemCobertura.getEndosoItem().getEndoso().getTipoItem().getEsGrupal()) {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura().multiply(new BigDecimal(blanket != null ? blanket.getCantidad() : null));
                    } else {
                        elMontoCobertura = endosoItemCobertura.getLimiteCobertura();
                    }

                } else if (endosoItemCobertura.getId() != null && !endosoItemCobertura.getId().equals("-1")) {
                    if (nemoTipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO))
                        if (endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.CV))
                            elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                        else
                            elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
                    else {
                        if ((nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) || nemoTipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)) &&
                                endosoItemCobertura.getMontoCobertura().compareTo(BigDecimal.ZERO) != 0)
                            elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                        else {
                            BigDecimal valorItemNuevoCobertura = calcularValorAseguradoTotal(endosoItemCobertura.getEndosoItem().getId(),
                                    true, null);
                            BigDecimal valorItemAnteriorCobertura = endosoItemCobertura.getEndosoItem().getValorAsegurado();
//                                if(debug) System.out.println("valorItem COBERTURAS ************************** = " + valorItemNuevoCobertura);
//                                if(debug) System.out.println("valorItem ENDOSOITEM ************************** = " + valorItemAnteriorCobertura);
//                                if (getEndosoItem().getValorItem() != getMontoCobertura())
                            if (!Objects.equals(valorItemAnteriorCobertura, valorItemNuevoCobertura) ||
                                    endosoItemCobertura.getCobertura().getGrupoCobertura().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.CV))
                                elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                            else {
                                //M.A.T.F  OT - 200700609 Calcula valor asegurado Riesgos Especiales y Robo
                                String ramoNM = endosoItemCobertura.getEndosoItem().getEndoso().getPoliza().getRamo().getNombreNemotecnico();
                                if (ramoNM.equals(RamoNemotecnico.RIESGOS_ESPECIALES)) {
                                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorAsegurado();
                                } else {
                                    elMontoCobertura = endosoItemCobertura.getMontoCobertura();
                                }
                            }

                        }
                    }
                } else
                    elMontoCobertura = endosoItemCobertura.getEndosoItem().getValorItem();
            }
        }

        return elMontoCobertura;

    }


    public void copiaCoberturaTexto(String usuario, EndosoItemCobertura endosoItemCobertura) {

        String codigo1 = endosoItemCobertura.getId();
        
        EndosoItemCoberturaTexto endosoItemCoberturaTexto;
        try (Stream<List<CoberturaTexto>> ignored1 = coberturaTextoRepository.getCoberturaTextoByCoberturaid(endosoItemCobertura.getCobertura().getId()).stream()) {
            List<CoberturaTexto> endosoItemCoberturaList = coberturaTextoRepository.getCoberturaTextoByCoberturaid(endosoItemCobertura.getCobertura().getId()).orElse(new ArrayList<>());
            for (CoberturaTexto coberturaTexto : endosoItemCoberturaList) {

                endosoItemCoberturaTexto = new EndosoItemCoberturaTexto();
                // F.M. 07/abr/2016 estaba creando conexiones por cada cobertura texto


                try (Stream<List<EndosoItemCoberturaTexto>> ignored =
                             endosoItemCoberturaTextoRepository.getEndosoItemCoberturaTextoByEndosoitemcoberturaidAndCoberturatextoid(endosoItemCobertura.getId(), coberturaTexto.getId()).stream()) {
                    List<EndosoItemCoberturaTexto> eictOp = endosoItemCoberturaTextoRepository.getEndosoItemCoberturaTextoByEndosoitemcoberturaidAndCoberturatextoid(endosoItemCobertura.getId(), coberturaTexto.getId()).orElse(new ArrayList<>());
                    if (!eictOp.isEmpty()) {
                        endosoItemCoberturaTexto = eictOp.get(0);
                    }

                    endosoItemCoberturaTexto.setNombre("");
                    endosoItemCoberturaTexto.setTitulo(coberturaTexto.getTitulo());
                    endosoItemCoberturaTexto.setOrden(coberturaTexto.getOrden());
                    endosoItemCoberturaTexto.setEndosoitemcoberturaid(codigo1);
                    endosoItemCoberturaTexto.setUsuarioactualiza(usuario);
                    endosoItemCoberturaTexto.setCoberturatextoid(coberturaTexto.getId());
                    endosoItemCoberturaTexto.setAnexo(coberturaTexto.getAnexo());
                    endosoItemCoberturaTexto.setTexto(coberturaTexto.getNombre());
//            			endosoItemCoberturaTexto.save();
                    if (null == endosoItemCoberturaTexto.getId()) {
                        endosoItemCoberturaTextoRepository.saveAndFlush(endosoItemCoberturaTexto);
                    } else {
                        endosoItemCoberturaTextoRepository.updateEndosoItemCoberturaTextoById(endosoItemCoberturaTexto, endosoItemCoberturaTexto.getId());
                    }
                }
            }
        }

    }

    public BigDecimal calcularValorAseguradoTotal(String endosoItemId, Boolean afectaValorAsegurado,
                                                  String grupoCoberturaId) {

        BigDecimal total = BigDecimal.ZERO;
        List<EndosoItemCobertura> endososItemCobertura = endosoItemCoberturaDbRepository.getEndosoItemCoberturaByEndosoItemIdAndAfectaValorAseguradoAndCobertura_GrupoCoberturaId(endosoItemId, afectaValorAsegurado,
                grupoCoberturaId).orElse(new ArrayList<>());


        for (EndosoItemCobertura endosoItemCobertura : endososItemCobertura) {
            total = total.add(endosoItemCobertura.getMontoCobertura());
        }
        return total;

    }


    public Cobertura consultarCobertura(String id) {
        Optional<Cobertura> coberturaOptional = coberturaRepository.findById(id);
        return coberturaOptional.orElse(null);

    }


    @Transactional(readOnly = true)
    public BigDecimal consultarValorLimiteActual(String polizaId, String itemId, String coberturaId) {
        Optional<BigDecimal> valorLimiteActual = endosoItemCoberturaRepository.consultarValorLimiteActual(
                polizaId,
                itemId,
                coberturaId
        );
        return valorLimiteActual.orElse(BigDecimal.ZERO);
    }


    @Transactional(readOnly = true)
    public BigDecimal consultarValorLimiteCoberturaActual(String polizaId, String itemId, String coberturaId) {
        Optional<BigDecimal> valorLimiteCoberturaActual = endosoItemCoberturaRepository.consultarValorLimiteCoberturaActual(
                polizaId,
                itemId,
                coberturaId
        );
        return valorLimiteCoberturaActual.orElse(BigDecimal.ZERO);
    }

    public void eliminaDeducibleCobertura(EndosoItemCobertura endosoItemCobertura) {
        try {
            List<EndosoDeducible> result = endosoDeducibleRepository.getEndosoDeducibleByCriterioDeducibleIdAndEndosoItemCoberturaId(CriterioDeducibleId.COBERTURA_ESPECIFICA, endosoItemCobertura.getId()).orElse(new ArrayList<>());

            for (EndosoDeducible endosoDeducible : result) {
                borraDeducibleTipo(endosoDeducible);
                endosoDeducibleRepository.deleteEndosoDeducibleById(endosoDeducible.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar EndosoDeducible. ", e);
        }

    }

    public void eliminaEndosoItemCoberturaTexto(EndosoItemCobertura endosoItemCobertura) {
        try {
            List<EndosoItemCoberturaTexto> result = endosoItemCoberturaTextoRepository.getEndosoItemCoberturaTextoByEndosoitemcoberturaid(endosoItemCobertura.getId()).orElse(new ArrayList<>());

            for (EndosoItemCoberturaTexto endosoItemCoberturaTexto : result) {
                endosoItemCoberturaTextoRepository.deleteEndosoItemCoberturaTextoById(endosoItemCoberturaTexto.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar EndosoItemCoberturaTexto. ", e);
        }
    }

    public void borraDeducibleTipo(EndosoDeducible endosoDeducible) {
        try {
            List<EndosoDeducibleTipo> result = endosoDeducibleTipoRepository.getEndosoDeducibleTipoByEndosoDeducibleId(endosoDeducible.getId()).orElse(new ArrayList<>());
            for (EndosoDeducibleTipo endosoDeducibleTipo : result) {
                endosoDeducibleTipoRepository.deleteEndosoDeducibleTipoById(endosoDeducibleTipo.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar EndosoDeducibleTipo. ", e);
        }

    }

    public BigDecimal porcentajeComisionEIC(String pAgenteId, String pCoberturaId) {
        BigDecimal porcentaje = BigDecimal.ZERO;
        try {
            List<BigDecimal> comisionAgentePorcentajes = comisionAgenteRepository.getComisionAgentePorcentajeByAgenteIdAndCoberturaId(pAgenteId, pCoberturaId).orElse(new ArrayList<>());
            if (!comisionAgentePorcentajes.isEmpty()) {
                porcentaje = comisionAgentePorcentajes.get(0);
            }
        } catch (NoResultException e) {
            return porcentaje;
        } catch (Exception e) {
            logger.error("Error al consultar el porcentajeComisionEIC: " + e.getMessage(), e);

        }
        return porcentaje;
    }

    public void grabaDeducibles(String coberturaGeneralId, String criterioDeducibleId, String endosoItemCoberturaId,
                                String endosoId, String coberturaId, String usuarioActualiza) {
        Optional<CoberturaGeneral> coberturaGeneralOp = coberturaGeneralRepository.getCoberturaGeneralById(coberturaGeneralId);
        if (coberturaGeneralOp.isPresent()) {
            CoberturaGeneral coberturaGeneral = coberturaGeneralOp.get();

            List<EndosoDeducible> resultDeducible = buscarPorEndosoCriterioClaseId(
                    endosoId, criterioDeducibleId, coberturaGeneralId);

            for (EndosoDeducible endosoDeducible : resultDeducible) {
                String endosoDeducibleId = endosoDeducible.getId();
                if (coberturaGeneral.getCoberturaid().equals(coberturaId)) {
                    EndosoDeducible endosoDeducibleNuevo = new EndosoDeducible();
                    endosoDeducibleNuevo.setTexto(endosoDeducible.getTexto());
                    endosoDeducibleNuevo.setClaseid(endosoItemCoberturaId);
                    endosoDeducibleNuevo.setCriterioDeducibleId(CriterioDeducibleId.COBERTURA_ESPECIFICA);
                    endosoDeducibleNuevo.setEndosoid(endosoId);
                    endosoDeducibleNuevo.setUsuarioactualiza(usuarioActualiza);
                    endosoDeducibleNuevo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                    endosoDeducibleNuevo = endosoDeducibleRepository.saveAndFlush(endosoDeducibleNuevo);

                    

                    List<EndosoDeducibleTipo> resultDeducibleTipo = buscarPorEndosoDeducible(endosoDeducibleId);

                    for (EndosoDeducibleTipo endosoDeducibleTipo : resultDeducibleTipo) {
                        EndosoDeducibleTipo endosoDeducibleTipoNuevo = new EndosoDeducibleTipo();
                        endosoDeducibleTipoNuevo.setTexto(endosoDeducibleTipo.getTexto());
                        endosoDeducibleTipoNuevo.setEndosoDeducibleId(endosoDeducibleNuevo.getId());
                        endosoDeducibleTipoNuevo.setValor(endosoDeducibleTipo.getValor());
                        endosoDeducibleTipoNuevo.setTipoDeducibleId(endosoDeducibleTipo.getTipoDeducibleId());
                        endosoDeducibleTipoNuevo.setUsuarioactualiza(usuarioActualiza);
                        endosoDeducibleTipoNuevo.setFechaactualiza(Util.actualDateTime().toLocalDateTime().toLocalDate());
                        endosoDeducibleTipoRepository.saveAndFlush(endosoDeducibleTipoNuevo);
                    }
                }
            }
        }
    }

    @Transactional
    public void grabaCoberturasGeneralesTransporte(String usuarioActualiza, EndosoItem endosoItem, boolean llamadoDesdeCargo) {


        String endosoId = endosoItem.getEndoso().getId();
        if (llamadoDesdeCargo) {
            try (Stream<EndosoItemCobertura> endosoItemCoberturaStream = endosoItemCoberturaRepository.getEndosoItemCoberturaByEndosoItemId(endosoItem.getId()).orElse(new ArrayList<>())
                    .stream()
            ) {
                endosoItemCoberturaStream.forEach(eic -> {
                    try {
                        endosoItemCoberturaRepository.deleteEndosoItemCoberturaById(eic.getId());
                    } catch (Exception e) {
                        throw new RuntimeException("Error al borrar EndosoItemCobertura ", e);
                    }
                });
            }
        }

        Cobertura cobertura = new Cobertura();
        if (endosoItem.getTipoItemId() != null && endosoItem.getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO)
                && endosoItem.getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.APLICACION)) {
            cobertura.setId(obtenerCoberturaId(endosoItem));
        } else if (endosoItem.getTipoItemId() != null && endosoItem.getTipoItemId().equals(TipoItemId.TRANSPORTE_ABIERTO)
                && (endosoItem.getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.POLIZA)
                || endosoItem.getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.INCLUSION))) {
            cobertura.setId(obtenerCoberturaAbiertaId(endosoItem));
        } else if (endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG)
                || endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM)) {
            cobertura.setId(getCoberturaPrincipalVida(endosoItem.getEndoso().getPoliza().getRamo().getId()));
        } else {
            //Modificación para masivos F.M. 18/10/2006
            if (endosoItem.getEndoso().getUnidadNegocioId() != null && endosoItem.getEndoso().getUnidadNegocio().getNombre().equals(UnidadNegocioNombre.MASIVOS))
                cobertura.setId(getCoberturaPrincipal(endosoItem.getEndoso().getPoliza().getRamo().getId(), true));
            else
                cobertura.setId(getCoberturaPrincipal(endosoItem.getEndoso().getPoliza().getRamo().getId(), false));
        }

        Optional<Cobertura> coberturaOp = coberturaRepository.getCoberturaById(cobertura.getId());
        if(coberturaOp.isPresent()) {
            cobertura = coberturaOp.get();
        }
        EndosoItemCobertura endosoItemCobertura = new EndosoItemCobertura();
        endosoItemCobertura.setAfectaGrupo(cobertura.getAfectaGrupo());
        endosoItemCobertura.setAfectaValorAsegurado(cobertura.getAfectaValorAsegurado());
        endosoItemCobertura.setAfectaPrima(cobertura.getAfectaPrima());
        if (endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.VG)
                || endosoItem.getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals(RamoNemotecnico.AM)) {
            if (cobertura.getId().equals(CoberturaId._4000))
                endosoItemCobertura.setEsPrimaFija(true);
        }
        endosoItemCobertura.setOrden(cobertura.getOrden());
        endosoItemCobertura.setSeccion(cobertura.getSeccion());
        endosoItemCobertura.setTexto(cobertura.getTexto());
        endosoItemCobertura.setLimite(cobertura.getLimite());
        endosoItemCobertura.setEndosoItem(endosoItem);
//	        endosoItemCobertura.getEndosoItem().setId(endosoItem.getId());
        endosoItemCobertura.setCobertura(cobertura);
//	        endosoItemCobertura.getCobertura().setId(cobertura.getId());
        endosoItemCobertura.setTasaCobertura(new BigDecimal(0));
        endosoItemCobertura.setCoberturaGeneralId(null);
        endosoItemCobertura.setMontoCobertura(calcularMonto(endosoItemCobertura));
        endosoItemCobertura.setUsuarioActualiza(usuarioActualiza);
        endosoItemCobertura.setFechaActualiza(Util.actualDateTime().toLocalDateTime());
        //SETEO EL PORCENTAJE DE COMISION NORMAL DEL AGENTE SEGUN CONTRATO
        EndosoAgente agenteLiderId = getEndosoAgenteByEndosoIdAndTipoAgente(endosoId, TipoAgente.LIDER);
        double porcentajeComisionNormal;
        if (!endosoItem.getEndoso().getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.APLICACION))
            porcentajeComisionNormal = getPorcentajeComisionEIC(
                    agenteLiderId.getId(), cobertura.getId()).doubleValue();
        else {
            Endoso endoso = obtieneEndoso(TipoEndosoNemotecnico.POLIZA, endosoItem.getEndoso().getPoliza().getId());

            if (endoso.getPorcentajeComisionAgente().compareTo(BigDecimal.ZERO) != 0) {
                porcentajeComisionNormal = endoso.getPorcentajeComisionAgente().doubleValue();
            } else {
                porcentajeComisionNormal = getPorcentajeComisionEIC(
                        agenteLiderId.getId(), cobertura.getId()).doubleValue();
            }
        }


        endosoItemCobertura.setPorcentajeComisionNormal(new BigDecimal(porcentajeComisionNormal));
        /*
         TODO: revisar esta parte si se migra del codigo del ensurance (pantalla para crear predios) o no se migra
         TODO: clase: com.tandi.emision.poliza.EndosoItem metodo grabaCoberturasGenerales(String usuarioActualiza, PersistenceManager pm)
        	        if(this.getEndoso().getPoliza().getRamo().getNombreNemotecnico().equals("TR")){//NUEVA EMISIÓN TRANSPORTE 11/06/2015
        	        	if(getNumeroItem()==1 ){//VALIDO TRANSPORTE DEPÓSITO SÓLO CALCULA UN ITEM VHBV 10/06/2015
        	        		endosoItemCobertura.calculaPrima(0, pm);
        	        	}
        	        }else{
        	        	endosoItemCobertura.calculaPrima(0, pm);
        	        }
        */
        calculaPrimaCoberturaNeta(BigDecimal.ZERO, endosoItemCobertura, false, BigDecimal.ZERO.intValue(), null);
        endosoItemCobertura.setAplicaDeducible(false);
        endosoItemCobertura = endosoItemCoberturaRepository.saveAndFlush(endosoItemCobertura);
        copiaCoberturaTexto(usuarioActualiza, endosoItemCobertura);

    }

    private BigDecimal cantidadActualdelGrupo(String polizaId, String itemId) {
        BigDecimal cantidadExc ;

        cantidadExc = polizaRepository.getSumCantidadByPolizaIdAndItemId(polizaId, itemId).orElse(new BigDecimal("0"));
        return cantidadExc;
    }

    public String getCoberturaPrincipal(String ramoId, boolean esMasivo) {//Masivos Colonial
        String valor = "null";
        String masivo = "0";
        Optional<Ramo> ramoOp = ramoDbRepository.getRamoById(ramoId).orElse(new ArrayList<>())
                .stream()
                .findFirst();
        Ramo ramo = ramoOp.orElseThrow(() -> new ZurichErrorException("002", "Insurance isn't found with ID: " + ramoId));
        String nemoRamo = ramo.getNombreNemotecnico();

        //se va a insertar las coberturas de incendio y transporte en el diccionario de coberturas solo para masivos 08/08/2008  Paul Falconi
        if (esMasivo && (nemoRamo.equals(RamoNemotecnico.RIESGOS_ESPECIALES) || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_MASIVOS)
                || nemoRamo.equals(RamoNemotecnico.VEHICULOS_LIVIANOS_MASIVOS) || nemoRamo.equals(RamoNemotecnico.VEHICULOS_PESADOS_MASIVOS) || nemoRamo.equals(RamoNemotecnico.VEHICULOS) || nemoRamo.equals(RamoNemotecnico.VEHICULOS_MASIVOS)
                || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES) || nemoRamo.equals(RamoNemotecnico.ACCIDENTES_PERSONALES_GUARDIAS) || nemoRamo.equals(RamoNemotecnico.RIESGOS_ESPECIALES_AP) || nemoRamo.equals(RamoNemotecnico.ROBO_ASALTO)))
            masivo = "1";

        List<String> result = coberturaRepository.getCoberturaIdByRamoIdAndEsMasivo(ramoId, !masivo.equals("0")).orElse(new ArrayList<>());
        if (!result.isEmpty()) {
            valor = result.get(0);
        }
        return valor;

    }

    public String getCoberturaPrincipalVida(String ramoId) {
        String valor = "null";

        List<String> result = coberturaRepository.getCoberturaIdByRamoId(ramoId).orElse(new ArrayList<>());
        if (!result.isEmpty()) {
            valor = result.get(0);
        }
        return valor;
    }

    private BigDecimal getValorLimiteCoberturaActualVG(String polizaId, String itemId, String coberturaId) {
        BigDecimal valorLimite;

        valorLimite = polizaRepository.getSumLimiteCoberturaByPolizaIdAndItemIdAndCoberturaId(polizaId, itemId, coberturaId).orElse(BigDecimal.ZERO);

        return valorLimite;
    }


    public Endoso obtieneEndoso(String nemoEndoso, String polizaId) {

        Endoso endosoPol = null;

        List<Endoso> endosos = endosoDbRepository.getEndosoByPolisaIdAndNemotecnicoAndNotEsAjusteVigencia(polizaId, nemoEndoso, true).orElse(new ArrayList<>());
        if (!endosos.isEmpty())
            endosoPol = endosos.get(0);
        return endosoPol;

    }

    public List<EndosoDeducible> buscarPorEndosoCriterioClaseId(String elEndosoId, String elCriterioDeducibleId, String laClaseId) {
        return endosoDeducibleRepository.getEndosoDeducibleByEndosoidAndCriterioDeducibleIdAndEndosoItemId(elEndosoId, elCriterioDeducibleId, laClaseId).orElse(new ArrayList<>());
    }

    public List<EndosoDeducibleTipo> buscarPorEndosoDeducible(String endosoDeducibleId) {

        return endosoDeducibleTipoRepository.getEndosoDeducibleTipoByEndosoDeducibleId(endosoDeducibleId).orElse(new ArrayList<>());

    }

    private String obtenerCoberturaId(EndosoItem endosoItem) {
        String resultado = "";
        List<AplicacionTransporte> result = aplicacionTransporteRepository.getAplicacionTransporteById(endosoItem.getItemId()).orElse(new ArrayList<>());
        if (!result.isEmpty()) {
            resultado = result.get(0).getCoberturaid();
        }
        return resultado;
    }

    private String obtenerCoberturaAbiertaId(EndosoItem endosoItem) {
        String resultado = "";
        List<TransporteAbierto> result = transporteAbiertoRepository.getTransporteAbiertoById(endosoItem.getItemId()).orElse(new ArrayList<>());
        if (!result.isEmpty()) {
            resultado = result.get(0).getCoberturaid();
        }
        return resultado;
    }


}
