package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PUNTOVENTA", schema = "SA")
public class PuntoVenta {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 35, nullable = false)
    private String nombre;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "SUCURSALID", length = 16)
    private String sucursalId;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "AGENTEID", length = 16)
    private String agenteId;

    @Column(name = "TIPOIMPRESION", length = 1, columnDefinition = "CHAR default '0'")
    private Boolean tipoImpresion;

    @Column(name = "TIPO", length = 1)
    private Boolean tipo;

    @Column(name = "UNIDADPRODUCCIONID", length = 16)
    private String unidadProduccionId;

    @Column(name = "CANALVENTAID", length = 16)
    private String canalVentaId;

    @Column(name = "USUARIOTPAID", length = 16)
    private String usuarioTpaId;

    @Column(name = "CONSOLIDADO", length = 1, columnDefinition = "CHAR default '0'")
    private Boolean consolidado;

    @Column(name = "TIPONEGOCIO", length = 1, columnDefinition = "CHAR default '0'")
    private Boolean tipoNegocio;

    @Column(name = "CODIGOSRI", length = 10)
    private String codigoSri;

    @Column(name = "PUNTOVENTAFACTURACIONID", length = 16)
    private String puntoVentaFacturacionId;

    @Column(name = "ESSOAT")
    private Boolean esSoat;

    @Column(name = "ESSALVAMENTO")
    private Boolean esSalvamento;

    @Column(name = "AUTORIZACIONBROKERID", length = 16)
    private String autorizacionBrokerId;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}
