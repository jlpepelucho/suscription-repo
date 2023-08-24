package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RAMO", schema = "SA")
@Data
public class Ramo {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DIVIDENDOTASA", nullable = false)
    private BigDecimal dividendoTasa;

    @Column(name = "FECHARESOLUCION", nullable = false)
    private LocalDate fechaResolucion;

    @Column(name = "NOMBRE", nullable = false, length = 55)
    private String nombre;

    @Column(name = "NOMBRENEMOTECNICO", nullable = false, length = 40)
    private String nombreNemotecnico;

    @Column(name = "NUMERORESOLUCION", nullable = false, length = 40)
    private String numeroResolucion;

    @Column(name = "CODIGOCONTABLE", nullable = false, length = 2)
    private String codigoContable;

    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "CUPOSSINIESTROID", length = 16)
    private String cuposSiniestroid;

    @Column(name = "NOMBRECONTABLE", length = 40)
    private String nombreContable;

    @Column(name = "ESFIANZA", length = 1)
    private String esFianza;

    @Column(name = "NUEVOCODCONTABLE", length = 7)
    private String nuevoCodigoContable;

    @Column(name = "NUEVONOMBRECONTABLE", length = 40)
    private String nuevoNombreContable;

    @Column(name = "TIPORAMOID", length = 16)
    private String tipoRamoId;

    @Column(name = "TASAPROMEDIO", columnDefinition = "NUMBER(9,4) DEFAULT 0")
    private BigDecimal tasaPromedio;

    @Column(name = "ESPRIMAEXCENTA")
    private Boolean esPrimaExcenta;

    @Column(name = "ORDEN", precision = 3)
    private Integer orden;

    @Column(name = "RESERVAMINIMA", nullable = false, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal reservaMinima;

    @Column(name = "CONTABILIZA", length = 1, columnDefinition = "CHAR(1) DEFAULT '1'")
    private String contabiliza;

    @Column(name = "LIMITECUMULO", precision = 19, scale = 4)
    private BigDecimal limiteCumulo;

    @Column(name = "NUMEROREGISTRO", length = 40)
    private String numeroRegistro;

    @Column(name = "FECHAREGISTRO")
    private LocalDate fechaRegistro;

    @Column(name = "PRODUCTGROUP", length = 500)
    private String productGroup;

    @Column(name = "RAMOSAP", length = 40)
    private String ramoSAP;

    @Column(name = "RAMOFIP", length = 40)
    private String ramoFIP;

    @Column(name = "DESCRIPCIONFIP", length = 500)
    private String descripcionFIP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPORAMOID",  insertable = false, updatable = false)
    private TipoRamo tipoRamo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

    @Builder
    public Ramo(String codigoContable) {
        this.codigoContable = codigoContable;
    }
}

