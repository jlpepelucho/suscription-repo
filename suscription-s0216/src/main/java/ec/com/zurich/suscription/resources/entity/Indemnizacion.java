package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "INDEMNIZACION")
public class Indemnizacion {

    @Column(name = "FECHACONTABILIZACION")
    private LocalDateTime fechaContabilizacion;

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "VALORNOCUBIERTO")
    private BigDecimal valorNoCubierto;

    @Column(name = "INFRASEGURO")
    private BigDecimal infraseguro;

    @Column(name = "DEPRECIACION")
    private BigDecimal depreciacion;

    @Column(name = "DEDUCIBLE")
    private BigDecimal deducible;

    @Column(name = "GENERANOTADEBITO")
    private Character generaNotaDebito;

    @Column(name = "GENERARASA")
    private Character generarAsa;

    @Column(name = "DESCUENTARASA")
    private Character descontarAsa;

    @Column(name = "VALORRASA")
    private BigDecimal valorRasa;

    @Column(name = "VALORINDEMNIZACION")
    private BigDecimal valorIndemnizacion;

    @Column(name = "OTROSDESCUENTOS")
    private BigDecimal otrosDescuentos;

    @Column(name = "CONCEPTOOTROSDESCUENTOS")
    private String conceptoOtrosDescuentos;

    @Column(name = "VALORPAGO")
    private BigDecimal valorPago;

    @Column(name = "TIENEDOCUMENTOSSOPORTE")
    private Character tieneDocumentoSoporte;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "TIPOINDEMNIZACIONID")
    private String tipoIndemnizacionId;

    @Column(name = "SUBRECLAMOID")
    private String subreclamoId;

    @Column(name = "NOTASDEBITOCREDITOID")
    private String notasDebitoCreditoId;

    @Column(name = "ENDOSODEDUCIBLEID")
    private String endosoDeducibleId;

    @Column(name = "MONEDAID")
    private String monedaId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "BENEFICIARIOID")
    private String beneficiarioId;

    @Column(name = "NOMBREBENEFICIARIO")
    private String nombreBeneficiario;

    @Column(name = "ENDOSORASAID")
    private String endosoRasaId;

    @Column(name = "VALORREPARTO")
    private BigDecimal valorReparto;

    @Column(name = "FECHA")
    private LocalDateTime fecha;

    @Column(name = "ESRASAAUTOMATICO")
    private Character esRasaAutomatico;

    @Column(name = "ESDEDUCIBLEAUTOMATICO")
    private Character esDeducibleAutomatico;

    @Column(name = "VALORIMPUESTOS")
    private BigDecimal valorImpuestos;

    @Column(name = "NUMEROACTA")
    private Long numeroActa;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "ENDOSOEXCLUSIONID")
    private String endosoExclusionId;

    @Column(name = "GENERAEXCLUSION")
    private Character generaExclusion;

    @Column(name = "VALOREXCLUSION")
    private BigDecimal valorExclusion;

    @Column(name = "ENDOSODEDUCIBLETIPOID")
    private String endosoDeducibleTipoId;

    @Column(name = "ESPAGOGRACIA")
    private Character esPagoGracia;

    @Column(name = "VALORPROFORMADO")
    private BigDecimal valorProformado;

    @Column(name = "VALORRETENCION")
    private BigDecimal valorRetencion;

    @Column(name = "TIPOSEGUROACEPTADOID")
    private String tipoSeguroAceptadoId;

    @Column(name = "FECHAENTREGATPA")
    private LocalDateTime fechaEntregaTPA;

    @Column(name = "FECHAAJUSTE")
    private LocalDateTime fechaAjuste;

    @Column(name = "VALORNEGADO")
    private BigDecimal valorNegado;

    @Column(name = "MOTIVONEGACION")
    private String motivoNegacion;

    @Column(name = "FECHADOCUMENTACION")
    private LocalDateTime fechaDocumentacion;

    @Column(name = "ESEXTERIOR")
    private Character esExterior;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}
