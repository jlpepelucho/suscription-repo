package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "NOTASDEBITOCREDITO")
@NoArgsConstructor
@AllArgsConstructor
public class NotasDebitoCredito extends Documento implements Serializable {

    @Column(name = "CLASEID", length = 16, nullable = false)
    private String claseId;

    @Column(name = "CLASE", length = 50, nullable = false)
    private String clase;

    @Column(name = "VALORPALABRAS", length = 200)
    private String valorPalabras;

    @Column(name = "RAMOID", length = 16)
    private String ramoId;

    @Column(name = "TIPOCONTRATOID", length = 16)
    private String tipoContratoId;

    @Column(name = "ANIOSUSCRIPCION", precision = 10)
    private Integer anioSuscripcion;

    @Column(name = "GRUPOCOBERTURAID", length = 16)
    private String grupoCoberturaId;

    @Column(name = "ESMIGRADO")
    private Boolean esMigrado;

    @Column(name = "ESREEMPLAZADO", length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String esReemplazado;

    @Column(name = "RECLAMOID", length = 16)
    private String reclamoId;

    @Column(name = "REPOSICIONID", length = 16)
    private String reposicionId;

    @Column(name = "BENEFICIARIOID", length = 16)
    private String beneficiarioId;

    @Column(name = "ESTAIMPRESA", length = 1)
    private Boolean estaImpresa;

    @Column(name = "TIENECASHLOSS", length = 1)
    private String tieneCashLoss;

    @Column(name = "ESTRIBUTABLE", length = 1, columnDefinition = "CHAR(1) DEFAULT '1'")
    private String esTributable;

    @Column(name = "ACUERDOID", length = 16)
    private String acuerdoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOCONTRATOID",  insertable = false, updatable = false)
    private TipoContratoReaseguro tipoContratoReaseguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRUPOCOBERTURAID",  insertable = false, updatable = false)
    private GrupoCobertura grupoCobertura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAMOID",  insertable = false, updatable = false)
    private Ramo ramo;


}