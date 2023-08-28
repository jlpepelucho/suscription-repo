package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "TRANSPORTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transporte implements Serializable {

        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
        @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
        private String id;

        @Column(name = "DESCRIPCION", length = 2000, nullable = false)
        private String descripcion;

        @Column(name = "COMPANIATRANSPORTES", length = 40)
        private String companiaTransportes;

        @Column(name = "TIPOEMBALAJEID", length = 16)
        private String tipoEmbalajeId;

        @Column(name = "VALORPRIMADEPOSITO", precision = 19, scale = 4, nullable = false)
        private BigDecimal valorPrimaDeposito;

        @Column(name = "PORCENTAJEPRIMADEPOSITO", precision = 19, scale = 4, nullable = false)
        private BigDecimal porcentajePrimaDeposito;

        @Column(name = "ANIOFABRICACIONBUQUE", precision = 10)
        private Integer anioFabricacionBuque;

        @Column(name = "NOMBREBUQUEMARINO", length = 40)
        private String nombreBuqueMarino;

        @Column(name = "SUBRAMOTRANSPORTEID", length = 16, nullable = false)
        private String subramoTransporteId;

        @Column(name = "TIPOMERCADERIAID", length = 16, nullable = false)
        private String tipoMercaderiaId;

        @Column(name = "CONDICIONTRANSPORTEID", length = 16)
        private String condicionTransporteId;

        @Column(name = "TIPOTRANSPORTEID", length = 16, nullable = false)
        private String tipoTransporteId;

        @Column(name = "FECHAACTUALIZA", nullable = false)
        private Timestamp fechaActualiza;

        @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
        private String usuarioActualiza;

        @Column(name = "PUERTOORIGENID", length = 16)
        private String puertoOrigenId;

        @Column(name = "PUERTODESTINOID", length = 16)
        private String puertoDestinoId;

        @Column(name = "VALORLIMITEESTIMADOANUAL", precision = 19, scale = 4)
        private BigDecimal valorLimiteEstimadoAnual;

        @Column(name = "VALORLIMITEEMBARQUE", precision = 19, scale = 4)
        private BigDecimal valorLimiteEmbarque;

        @Column(name = "TASA", precision = 19, scale = 4)
        private BigDecimal tasa;

        @Column(name = "PORCENTAJESOBRESEGURO", precision = 19, scale = 4)
        private BigDecimal porcentajeSobreseguro;

        @Column(name = "FORMACALCULO", columnDefinition = "CHAR(1)")
        private Character formaCalculo;

        @Column(name = "TXTORIGEN", length = 3000)
        private String txtOrigen;

        @Column(name = "TXTDESTINO", length = 3000)
        private String txtDestino;

        // Relationships
        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "PUERTODESTINOID", insertable = false, updatable = false)
        private PuertoGeneral puertoDestino;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "PUERTOORIGENID", insertable = false, updatable = false)
        private PuertoGeneral puertoOrigen;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "CONDICIONTRANSPORTEID", insertable = false, updatable = false)
        private CondicionTransporte condicionTransporte;



        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "SUBRAMOTRANSPORTEID", insertable = false, updatable = false)
        private SubRamoTransporte subramoTransporte;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "TIPOEMBALAJEID", insertable = false, updatable = false)
        private TipoEmbalaje tipoEmbalaje;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "TIPOMERCADERIAID", insertable = false, updatable = false)
        private TipoMercaderia tipoMercaderia;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "TIPOTRANSPORTEID", insertable = false, updatable = false)
        private TipoTransporte tipoTransporte;


        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
        private Item item;
}
