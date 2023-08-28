package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.library.exceptions.ZurichErrorException;
import ec.com.zurich.suscription.repository.*;
import ec.com.zurich.suscription.resources.entity.*;
import ec.com.zurich.suscription.service.EndosoDiferidoDbService;
import ec.com.zurich.suscription.util.TipoEndosoNemotecnico;
import ec.com.zurich.suscription.util.Util;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndosoDiferidoDbServiceImpl implements EndosoDiferidoDbService {

    private final EndosoDiferidoDbRepository endosoDiferidoDbRepository;
    private final EndosoDbRepository endosoDbRepository;
    private final EstadoDbRepository estadoDbRepository;
    private final EndosoItemCoberturaDbRepository endosoItemCoberturaDbRepository;
    private final RamoDbRepository ramoDbRepository;
    private final DistribucionEndosoDiferidoDbRepository distribucionEndosoDiferidoDbRepository;

    @Override
    @Transactional
    public String crearNuevoEndosoDiferido(String endosoId) {
        Endoso endosoBdd = endosoDbRepository.getEndosoById(endosoId).orElseThrow(() -> new ZurichErrorException("002", "Endorsement with ID " + endosoId + " not found"));
        EndosoDiferido endosoDiferidoResp =
                new EndosoDiferido();

        if (endosoBdd.getVigenciaHasta().isEqual(endosoBdd.getVigenciaDesde())) {
            throw new RuntimeException("\n" +
                    "The effective start date cannot be the same as the effective date until.");
        }
        if (endosoBdd.getVigenciaHasta().isBefore(endosoBdd.getVigenciaDesde())) {
            throw new RuntimeException("\n" +
                    "The effective start date cannot be earlier than the effective date from.");
        }
        BigDecimal totalPrimaNeta = endosoBdd.getValorPrimaNeta().setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalValorAsegurado = endosoBdd.getValorAsegurado().setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalComision = endosoBdd.getValorComision().setScale(2, RoundingMode.HALF_UP);
        OffsetDateTime fechaInicioPeriodo = null;
        OffsetDateTime fechaFinPeriodo = OffsetDateTime.of(endosoBdd.getVigenciaDesde(),
                ZoneOffset.UTC);
        BigDecimal primaAnual = null;
        BigDecimal primaAcumulada = BigDecimal.ZERO;
        BigDecimal valorAseguradoAnual = null;
        BigDecimal comisionAnual = null;
        BigDecimal comisionAcumulada = BigDecimal.ZERO;
        String usuarioActualiza = endosoBdd.getUsuarioActualiza();
        OffsetDateTime fechaActual = OffsetDateTime.of(Util.actualDateTime().toLocalDateTime(), ZoneOffset.UTC);
        long periodosEndoso;
        long diasEndoso;
        if (TipoEndosoNemotecnico.CANCELACION_POLIZA.equals(endosoBdd.getTipoEndoso().getNemotecnico())) {
            periodosEndoso = verificaPeriodos_POL(endosoBdd);
        } else {
            if (endosoBdd.getPoliza().getClasePolizaId().equals("2")
                    || (endosoBdd.getPoliza().getClasePolizaId().equals("3"))) {
                Timestamp fechaFin = Util
                        .yearAheadVigenciaDateTime(Timestamp.valueOf(endosoBdd.getVigenciaDesde()));
                periodosEndoso = Util.periodosTranscurridos(new Date(fechaFin.getTime()),
                        Util.getDateFromLocalDateTime(endosoBdd.getVigenciaDesde()));
            } else {
                periodosEndoso = Util.periodosTranscurridos(
                        Util.getDateFromLocalDateTime(endosoBdd.getVigenciaHasta()),
                        Util.getDateFromLocalDateTime(endosoBdd.getVigenciaDesde()));
            }
        }
        if (endosoBdd.getPoliza().getClasePolizaId().equals("2")
                || (endosoBdd.getPoliza().getClasePolizaId().equals("3"))) {
            Timestamp fechaFin = Util.yearAheadVigenciaDateTime(
                    Util.getDateFromLocalDateTime(endosoBdd.getVigenciaDesde()));
            diasEndoso = Util.diasTranscurridos(fechaFin,
                    Timestamp.valueOf(endosoBdd.getVigenciaDesde()));
        } else {
            diasEndoso = Util.diasTranscurridos(Timestamp.valueOf(endosoBdd.getVigenciaHasta()),
                    Timestamp.valueOf(endosoBdd.getVigenciaDesde()));
        }
        long iterador = 1;
        long cont = periodosEndoso;
        do {
            if (TipoEndosoNemotecnico.CANCELACION_POLIZA.equals(endosoBdd.getTipoEndoso().getNemotecnico()) && periodosEndoso > 1) {
                EndosoDiferido endoDifAnterior;
                endoDifAnterior = consultarEndosoDiferido(endosoBdd.getEndosoRef(), cont);

                if (endoDifAnterior != null) {
                    if (1 != cont) {
                        Estado estado = estadoDbRepository.getEstadoById("403").orElseThrow(() -> new ZurichErrorException("002", "Estate isn't found with ID: 403"));
                        endoDifAnterior.setEstadoId(estado.getId());
                        endosoDiferidoDbRepository.updateEstadoIdById(estado.getId(), endoDifAnterior.getId());
                    }
                    if (iterador == periodosEndoso) {
                        fechaInicioPeriodo = OffsetDateTime.of(endosoBdd.getVigenciaDesde(),
                                ZoneOffset.UTC);
                        fechaFinPeriodo = OffsetDateTime.of(endoDifAnterior.getVigenciaHasta(),
                                ZoneOffset.UTC);
                        primaAnual = totalPrimaNeta.subtract(primaAcumulada);
                        comisionAnual = totalComision.subtract(comisionAcumulada);
                    } else {
                        fechaInicioPeriodo = OffsetDateTime.of(endoDifAnterior.getVigenciaDesde(),
                                ZoneOffset.UTC);
                        fechaFinPeriodo = OffsetDateTime.of(endoDifAnterior.getVigenciaHasta(),
                                ZoneOffset.UTC);
                        primaAnual = endoDifAnterior.getValorPrimaNeta().multiply(new BigDecimal(-1)).setScale(2,
                                RoundingMode.HALF_UP);
                        comisionAnual = endoDifAnterior.getValorComisiones().multiply(new BigDecimal(-1))
                                .setScale(2, RoundingMode.HALF_UP);
                    }
                    valorAseguradoAnual = totalValorAsegurado;
                }
            } else {
                fechaInicioPeriodo = fechaFinPeriodo;
                Timestamp aux = Timestamp.valueOf(fechaFinPeriodo.toLocalDateTime());
                fechaFinPeriodo = (iterador == periodosEndoso)
                        ? OffsetDateTime.of(endosoBdd.getVigenciaHasta(), ZoneOffset.UTC)
                        : OffsetDateTime.of(Util.yearAheadVigenciaDateTime(aux).toLocalDateTime(), ZoneOffset.UTC);
                if (iterador == periodosEndoso) {
                    primaAnual = totalPrimaNeta.subtract(primaAcumulada);
                    comisionAnual = totalComision.subtract(comisionAcumulada);
                } else {
                    primaAnual = totalPrimaNeta.multiply(
                            new BigDecimal(Util.diasTranscurridos(Timestamp.valueOf(fechaFinPeriodo.toLocalDateTime()),
                                    Timestamp.valueOf(fechaInicioPeriodo.toLocalDateTime()))));
                    primaAnual = primaAnual.divide(new BigDecimal(diasEndoso), 2, RoundingMode.HALF_UP);
                    comisionAnual = totalComision.multiply(
                            new BigDecimal(Util.diasTranscurridos(Timestamp.valueOf(fechaFinPeriodo.toLocalDateTime()),
                                    Timestamp.valueOf(fechaInicioPeriodo.toLocalDateTime()))));
                    comisionAnual = comisionAnual.divide(new BigDecimal(diasEndoso), 2, RoundingMode.HALF_UP);
                }
                valorAseguradoAnual = totalValorAsegurado;
            }
            boolean esNuevo = false;
            EndosoDiferido endosoDiferido;

            if (TipoEndosoNemotecnico.CANCELACION_POLIZA.equals(endosoBdd.getTipoEndoso().getNemotecnico())) {
                endosoDiferido = consultarEndosoDiferido(endosoBdd.getId(), cont);

            } else {
                endosoDiferido = consultarEndosoDiferido(endosoBdd.getId(), iterador);

            }

            if (endosoDiferido == null) {
                endosoDiferido = new EndosoDiferido();
                esNuevo = true;
            }

            endosoDiferido.setVigenciaDesde(fechaInicioPeriodo != null ? fechaInicioPeriodo.toLocalDateTime() : null);
            endosoDiferido.setVigenciaHasta((fechaFinPeriodo.toLocalDateTime()));
            Estado estado = estadoDbRepository.getEstadoById("401").orElseThrow(() -> new ZurichErrorException("002", "Estate isn't found with ID: 401"));
            endosoDiferido.setEstadoId(estado.getId());
            if (TipoEndosoNemotecnico.CANCELACION_POLIZA.equals(endosoBdd.getTipoEndoso().getNemotecnico())) {
                endosoDiferido.setAnio((int) cont);
                if (1 != cont) {
                    Estado est = estadoDbRepository.getEstadoById("403").orElseThrow(() -> new ZurichErrorException("002", "Estate isn't found with ID: 403"));
                    endosoDiferido.setEstadoId(est.getId());
                }
            } else {
                endosoDiferido.setAnio((int) iterador);
            }
            endosoDiferido.setEndoso(endosoBdd);
            endosoDiferido.setValorPrimaNeta(primaAnual);
            endosoDiferido.setValorAsegurado(valorAseguradoAnual);
            endosoDiferido.setValorComisiones(comisionAnual);
            endosoDiferido.setEsDatoOtroSistema(false);
            endosoDiferido.setUsuarioActualiza(usuarioActualiza);
            endosoDiferido.setFechaActualiza(fechaActual.toLocalDateTime());
            if (esNuevo) {
                endosoDiferidoResp = endosoDiferidoDbRepository.save(endosoDiferido);
            } else {
                endosoDiferidoDbRepository.updateEndosoDiferidoById(endosoDiferido, endosoDiferido.getId());
            }
            primaAcumulada = primaAcumulada.add(primaAnual);
            comisionAcumulada = comisionAcumulada.add(comisionAnual);

//				endosoBdd.setEsPrimaAnticipada(false);
            endosoDbRepository.save(endosoBdd);


            if (Util.getDateFromOffSetDateTime(
                            OffsetDateTime.of(endosoBdd.getVigenciaDesde(), ZoneOffset.UTC))
                    .after(Util.stringToDate(
                            Util.LastDayMonth(Util.fechaIncrementoPeriodoTiempo(Util.actualDate(), 1, 2))))) {
                endosoBdd.setEsPrimaAnticipada(true);
                endosoDbRepository.updateEndoso(endosoBdd.getId(), endosoBdd);
            } else {
                endosoBdd.setEsPrimaAnticipada(false);
                endosoDbRepository.updateEndoso(endosoBdd.getId(), endosoBdd);
            }

            if (endosoDiferido.getValorPrimaNeta().compareTo(BigDecimal.ZERO) != 0) {
                guardaDistribucionEIGC(endosoDiferido, usuarioActualiza);
            }

            iterador++;
            cont--;
        } while (iterador <= periodosEndoso);

        return endosoDiferidoResp.getId();
    }

    @Transactional()
    public EndosoDiferido consultarEndosoDiferido(String endosoRef, long anio) {

        EndosoDiferido ed = new EndosoDiferido();
        try {
            List<EndosoDiferido> endosos = endosoDiferidoDbRepository.getEndosoDiferidoByEndosoIdAndAnio(endosoRef, (int) anio).orElse(new ArrayList<>());

            if (!endosos.isEmpty()) {
                ed = endosos.get(0);
            }
        } catch (NoResultException e) {
            ed = null;
        }
        return ed;
    }

    public long verificaPeriodos_POL(Endoso endosoCNP) {
        long periodos = 0;
        List<EndosoDiferido> endosoDiferidoList = endosoDiferidoDbRepository
                .getEndosoDiferidoByEndosoId(endosoCNP.getEndosoRef()).orElse(new ArrayList<>());
        for (EndosoDiferido endosoDiferido : endosoDiferidoList) {
            if ((!endosoCNP.getVigenciaDesde().toLocalTime().isBefore(endosoDiferido.getVigenciaDesde().toLocalTime())
                    && !endosoCNP.getVigenciaDesde().toLocalTime().isAfter(endosoDiferido.getVigenciaHasta().toLocalTime()))
                    || periodos > 0) {
                periodos++;
            }
        }

        return periodos;
    }

    public void guardaDistribucionEIGC(EndosoDiferido endosoDiferido,
                                       String usuarioActualiza) {

        String endosoDiferidoId = endosoDiferido.getId();
        List<Object[]> resultados = endosoItemCoberturaDbRepository
                .getEndosoItemCoberturaByEndosoDiferidoIdAndValorPrima(endosoDiferidoId,
                        endosoDiferido.getValorPrimaNeta())
                .orElse(new ArrayList<>());
        for (Object[] obj : resultados) {
            BigDecimal valorprimaneta = new BigDecimal(obj[3].toString());
            BigDecimal porcentaje = new BigDecimal(obj[2].toString());
            String ramoid = obj[3].toString();
            String codcontable = obj[3].toString();
            nuevo(endosoDiferidoId, valorprimaneta, porcentaje, ramoid, codcontable, usuarioActualiza);
        }

    }

    public void nuevo(String endosoDiferidoId, BigDecimal valorDistribuido, BigDecimal porcentaje,
                      String ramoId, String codigoContable, String usuarioActualiza) {

        DistribucionEndosoDiferido distribucionED = new DistribucionEndosoDiferido();
        distribucionED.setValorLocal(valorDistribuido);
        distribucionED.setValorExterior(valorDistribuido);
        distribucionED.setValorOrigen(valorDistribuido);
        EndosoDiferido ed = endosoDiferidoDbRepository.getEndosoDiferidoById(endosoDiferidoId).orElse(new ArrayList<>())
                .stream()
                .findFirst().orElseThrow(() -> new ZurichErrorException("002",
                        "Deferred Endorsement isn't found with ID: " + endosoDiferidoId));
        distribucionED.setEndosoDiferido(ed);
        Ramo r = ramoDbRepository.getRamoById(ramoId).orElse(new ArrayList<>())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ZurichErrorException("002", "Insurance isn't found with ID: " + ramoId));
        distribucionED.setRamo(r);
        distribucionED.setCodContableGc(codigoContable);
        distribucionED.setPorcentaje(porcentaje);
        distribucionED.setUsuarioActualiza(usuarioActualiza);
        distribucionED.setFechaActualiza(new Timestamp(Date.valueOf(LocalDateTime.now().toLocalDate()).getTime()));
        distribucionEndosoDiferidoDbRepository.save(distribucionED);

    }
}
