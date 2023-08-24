package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DOCUMENTOXITEM", schema = "SA")
public class DocumentoXItem {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DETALLEITEMID")
    private String detalleItemId;

    @Column(name = "CLIENTEID")
    private String clienteId;

    @Column(name = "FECHACREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "SALDO")
    private BigDecimal saldo;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "ENDOSOID")
    private String endosoId;

    @Column(name = "PRIMAS")
    private BigDecimal primas;

    @Column(name = "PRIMASEXC")
    private BigDecimal primasExc;

    @Column(name = "DERECHOSEMISION")
    private BigDecimal derechosEmision;

    @Column(name = "SUPERBANCOS")
    private BigDecimal superBancos;

    @Column(name = "SEGUROCAMPESINO")
    private BigDecimal seguroCampesino;

    @Column(name = "SUBTOTAL")
    private BigDecimal subtotal;

    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVAEXC")
    private BigDecimal ivaExc;

    @Column(name = "FINANCIACION")
    private BigDecimal financiacion;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "REFINANCIACION")
    private BigDecimal refinanciacion;

    @Column(name = "VALORBASE")
    private BigDecimal valorBase;

    @Column(name = "OTROSCONIVA")
    private BigDecimal otrosConIva;

    @Column(name = "AUTORIZACIONSRIID")
    private String autorizacionSRIId;

    @Column(name = "SUCURSALID")
    private String sucursalId;

    @Column(name = "TIPODOCUMENTOID")
    private String tipoDocumentoId;

    @Column(name = "PUNTOVENTAID")
    private String puntoVentaId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "PORCENTAJEIVA")
    private String porcentajeIva;

    @Column(name = "COMPENSACIONIVA")
    private BigDecimal compensacionIva;

    @Column(name = "PORCENTAJECOMPENSACIONIVA")
    private String porcentajeCompensacionIva;

    @Column(name = "OTROSSINIVA")
    private BigDecimal otrosSinIva;

    @Column(name = "TIENECORREOFACTURACION")
    private Character tieneCorreoFacturacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPODOCUMENTOID", updatable = false, insertable = false)
    private TipoDocumento tipoDocumento;
}
