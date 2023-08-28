package ec.com.zurich.suscription.service.impl;


import ec.com.zurich.suscription.resources.entity.EndosoItem;
import ec.com.zurich.suscription.service.EndosoItemCoberturaRestService;
import ec.com.zurich.suscription.service.EndosoItemDetalleDbService;
import ec.com.zurich.suscription.util.MasivosId;
import ec.com.zurich.suscription.util.MasivosNombre;
import ec.com.zurich.suscription.util.TipoEndosoNemotecnico;
import ec.com.zurich.suscription.util.TipoItemId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@AllArgsConstructor
public class EndosoItemCoberturaRestServiceImpl implements EndosoItemCoberturaRestService {
    private final EndosoItemDetalleDbService endosoItemDetalleDbService;

    @Override
    public String obtenerNombreCanalPorPolizaId(String polizaId) {

        String canalVacio = "";

        Set<String> canalBPAC = Set.of(
                MasivosId.BPAC1,
                MasivosId.BPAC2,
                MasivosId.BPAC3,
                MasivosId.BPAC4,
                MasivosId.BPAC5
        );
        Set<String> canalSIOUsados = Set.of(
                MasivosId.SIO_USADOS1
        );
        Set<String> canalSIONuevos = Set.of(
                MasivosId.SIO_NUEVOS1,
                MasivosId.SIO_NUEVOS2,
                MasivosId.SIO_NUEVOS3,
                MasivosId.SIO_NUEVOS4
        );
        Set<String> canalAmazonas = Set.of(
                MasivosId.AMAZONAS1,
                MasivosId.AMAZONAS2,
                MasivosId.AMAZONAS3,
                MasivosId.AMAZONAS4,
                MasivosId.AMAZONAS5
        );
        Set<String> canalOriginarsa = Set.of(
                MasivosId.ORIGINARSA1,
                MasivosId.ORIGINARSA2,
                MasivosId.ORIGINARSA3,
                MasivosId.ORIGINARSA4,
                MasivosId.ORIGINARSA5,
                MasivosId.ORIGINARSA6
        );

        if (canalBPAC.contains(polizaId)) {
            return MasivosNombre.CANAL_BPAC;
        } else if (canalSIOUsados.contains(polizaId)) {
            return MasivosNombre.CANAL_SIO_USADOS;
        } else if (canalSIONuevos.contains(polizaId)) {
            return MasivosNombre.CANAL_SIO_NUEVOS;
        } else if (canalAmazonas.contains(polizaId)) {
            return MasivosNombre.CANAL_AMAZONAS;
        } else if (canalOriginarsa.contains(polizaId)) {
            return MasivosNombre.CANAL_ORIGINARSA;
        }

        return canalVacio;
    }

    @Override
    public BigDecimal encuentraPrimaAMporTipoEndoso(EndosoItem endosoItem, String tipoEndoso) {
        BigDecimal prima = BigDecimal.ZERO;
        int numeroTipoEndoso = numeroTipoEndoso(tipoEndoso);
        switch (numeroTipoEndoso) {
            case 1, 2 -> prima = endosoItemDetalleDbService.calcularPrimaDeAM(endosoItem.getId());
            case 3, 4, 5, 8, 9 -> prima = endosoItem.getValorPrimaNeta();
        }
        return prima;
    }

    @Override
    public int numeroTipoEndoso(String tipoEndoso) {
        int resultado = 0;
        if (tipoEndoso.equals(TipoEndosoNemotecnico.POLIZA)) {
            resultado = 1;
        }
        // incendio
        if (tipoEndoso.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO)) {
            resultado = 2;
        }
        // Transporte
        if (tipoEndoso.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO)) {
            resultado = 3;
        }
        // Casco Buques
        if (tipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO)) {
            resultado = 4;
        }
        // Vehiculos
        if (tipoEndoso.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA)) {
            resultado = 5;
        }
        // Equipo y Maquinaria Contratista
        if (tipoEndoso.equals(TipoEndosoNemotecnico.CANCELACION_POLIZA)) {
            resultado = 6;
        }
        // Fidelidad Privada
        if (tipoEndoso.equals(TipoEndosoNemotecnico.RENOVACION)) {
            resultado = 7;
        }
        // Accidentes personales
        if (tipoEndoso.equals(TipoEndosoNemotecnico.EXCLUSION)) {
            resultado = 8;
        }
        // Garantia Aduanera
        if (tipoEndoso.equals(TipoEndosoNemotecnico.INCLUSION)) {
            resultado = 9;
        }
        return resultado;
    }

    @Override
    public boolean esTipoEndosoValido(String tipoEndosoNT) {
        return tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_AUMENTO) ||
                tipoEndosoNT.equals(TipoEndosoNemotecnico.AJUSTE_PRIMA_REBAJA) ||
                tipoEndosoNT.equals(TipoEndosoNemotecnico.AUMENTO_VALOR_ASEGURADO) ||
                tipoEndosoNT.equals(TipoEndosoNemotecnico.REBAJA_VALOR_ASEGURADO);
    }

    @Override
    public boolean esTipoItemIdValido(String tipoItemId) {
        return tipoItemId.equals(TipoItemId.TRANSPORTE_ABIERTO) || tipoItemId.equals(TipoItemId.TRANSPORTE_GENERAL);
    }

}
