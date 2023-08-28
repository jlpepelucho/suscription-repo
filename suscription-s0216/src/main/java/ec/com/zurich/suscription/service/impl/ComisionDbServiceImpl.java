package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.repository.EndosoAgenteDbRepository;
import ec.com.zurich.suscription.repository.EndosoDbRepository;
import ec.com.zurich.suscription.resources.entity.Endoso;
import ec.com.zurich.suscription.resources.entity.EndosoAgente;
import ec.com.zurich.suscription.service.ComisionDbService;
import ec.com.zurich.suscription.util.TipoEndosoNemotecnico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComisionDbServiceImpl implements ComisionDbService {

    private final EndosoAgenteDbRepository endosoAgenteDbRepository;
    private final EndosoDbRepository endosoDbRepository;


    @Override
    public void calcularComisiones(Endoso elEndoso) {
        String elEndosoId = elEndoso.getId();
        // SI EL VALOR O PORCENTAJE DE COMISION ES INGRESADO DESDE EL ENDOSO NO SE CALCULA POR COBERTURA
        List<EndosoAgente> endososAgente = endosoAgenteDbRepository.getEndosoAgenteByEndosoIdAndTipoAgente(elEndoso.getId(), "L").orElse(new ArrayList<>());
        String agenteLiderId = "";
        if (!endososAgente.isEmpty()) {
            agenteLiderId = endososAgente.get(0).getAgenteId();
        }
        if (!agenteLiderId.equals("1")) {
            if (elEndoso.getPorcentajeComisionAgente().compareTo(BigDecimal.ZERO) > 0 || elEndoso.getValorComision().compareTo(BigDecimal.ZERO) > 0) {
                // SI SE INGRESO EL VALOR DE COMISION CALCULO EL PORCENTAJE EN BASE A EL VALOR DE PRIMA NETA Y GRABO EN LA TABLA ENDOSO
//	                    if (endoso.getValorComision() > 0) {
//	                        BigDecimal valorComisionEn = new BigDecimal(new Double(endoso.getValorComision()).toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
//	                        BigDecimal valorPrimaNetaEn = new BigDecimal(new Double(endoso.getValorPrimaNeta()).toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
//	                        //float porcenComiEndoso = (float) ((endoso.getValorComision() * 100) / endoso.getValorPrimaNeta());
//	                        BigDecimal porcenComiEndoso = valorComisionEn.multiply(new BigDecimal(100)).divide(valorPrimaNetaEn, 2, BigDecimal.ROUND_HALF_UP);
//	                        endoso.setPorcComisionAgente(porcenComiEndoso.floatValue());
//	                    }
                // SI SE INGRESO EL PORCENTAJE DE COMISION CALCULO EL VALOR EN BASE A EL VALOR DE PRIMA NETA Y GRABO EN LA TABLA ENDOSO
                if (elEndoso.getPorcentajeComisionAgente().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal valorPrimaNetaEn = elEndoso.getValorPrimaNeta().setScale(2, RoundingMode.HALF_UP);
                    BigDecimal valorPorcComisionAgente = elEndoso.getPorcentajeComisionAgente().setScale(5, RoundingMode.HALF_UP);
                    //double valComiEndoso = (endoso.getValorPrimaNeta() * endoso.getPorcentajeComisionAgente()) / 100;
                    BigDecimal valComiEndoso = (valorPrimaNetaEn.multiply(valorPorcComisionAgente)).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    elEndoso.setValorComision(valComiEndoso);
                }
                endosoDbRepository.updateEndoso(elEndoso.getId(), elEndoso);

            } else {
                // SI EL VALOR O PORCENTAJE DE COMISION NO ES INGRESADO DESDE EL ENDOSO SE CALCULA POR ENDOSO ITEM COBERTURA


                List<Object[]> resultados = endosoDbRepository.getAllByEndosoId(elEndosoId).orElse(new ArrayList<>());
                BigDecimal totalPrimaNeta = BigDecimal.ZERO;
                BigDecimal totalComision = BigDecimal.ZERO;
                BigDecimal comisionCobertura;

                for (Object[] obj : resultados) {
                    String endosoId = obj[2].toString();

                    if (elEndosoId.equals(endosoId)) {
                        //String afectaPrima = resultSetComisionAgentes.getString("afectaPrima");
                        BigDecimal valorPrimaCoberturaBD = new BigDecimal(obj[1].toString());
                        BigDecimal porcentajeCoberturaBD = new BigDecimal(obj[3].toString());
                        BigDecimal porcentajeComisionEspecial = new BigDecimal(obj[4].toString());

                        if (porcentajeComisionEspecial.compareTo(BigDecimal.ZERO) > 0) {
//	                            porcentajeCobertura = porcentajeComisionEspecial;
                            porcentajeCoberturaBD = porcentajeComisionEspecial;
                        }
                        //           if (afectaPrima.equals("1")) {
                        //comisionCobertura = (porcentajeCobertura / 100.0) * valorPrimaCobertura;
                        comisionCobertura = (porcentajeCoberturaBD.multiply(valorPrimaCoberturaBD)).divide(new BigDecimal(100));
                        //totalPrimaNeta = (totalPrimaNeta + valorPrimaCobertura);
                        totalPrimaNeta = (totalPrimaNeta.add(valorPrimaCoberturaBD));
                        //totalComision = (comisionCobertura + totalComision);
                        totalComision = (comisionCobertura.add(totalComision));
                    }
                    //     }
                }
                // SI NO SE CALCULA EL TOTAL DE PRIMA NETA POR ENDOSO ITEM COBERTURA EL TOTAL PRIMA NETA ES EL DEL ENDOSO
                BigDecimal valorPrimaNeta = elEndoso.getValorPrimaNeta();
                if (totalPrimaNeta.equals(BigDecimal.ZERO)) {
                    totalPrimaNeta = valorPrimaNeta;
                }

                //float porcentajeComision = 0;
                BigDecimal porcentajeComision = BigDecimal.ZERO;
                if (!elEndoso.getPoliza().getClasePolizaId().equals("2") || elEndoso.getTipoEndoso().getNemotecnico().equals(TipoEndosoNemotecnico.APLICACION)) {
                    //porcentajeComision = (float) (totalComision / totalPrimaNeta) * 100;
                    if (totalPrimaNeta.doubleValue() > 0) {
                        porcentajeComision = totalComision.multiply(new BigDecimal(100)).divide(totalPrimaNeta, 2, RoundingMode.HALF_UP);
                    }
                }

                elEndoso.setValorComision(totalComision.setScale(3, RoundingMode.HALF_UP));
                elEndoso.setPorcentajeComisionAgente(porcentajeComision);
                endosoDbRepository.updateEndoso(elEndoso.getId(), elEndoso);

            }
        }
    }
}
