package ec.com.zurich.suscription.resources.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "GRUPOCOBERTURA", schema = "SA")
public class GrupoCobertura {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 40, nullable = false)
    private String nombre;

    @Column(name = "NOMBRENEMOTECNICO", length = 10)
    private String nombreNemotecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAMOID", insertable = false, updatable = false)
    private Ramo ramo;

    @Column(name = "RAMOID", length = 16)
    private String ramoId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "TIENECESIONALCIEN")
    private Boolean tieneCesionAlCien;

    @Column(name = "CODCONTABLE", length = 7)
    private String codigoContable;

    @Column(name = "NOMBRECONTABLE", length = 40)
    private String nombreContable;

    @Column(name = "FORMULA", length = 100)
    private String formula;

    @Column(name = "ORDEN", precision = 10)
    private Integer orden;

    @Column(name = "NOMBRE_SUPER", length = 40)
    private String nombreSuper;

    @Column(name = "SUMAALTOTAL", length = 1)
    private String sumaAlTotal;

    @Column(name = "CUENTAPOLIZATOTAL", length = 1)
    private String cuentaPolizaTotal;

    @Column(name = "SUMATOTAL", precision = 12, scale = 4)
    private Double sumaTotal;

    @Column(name = "CONTROLASUMA")
    private Boolean controlaSuma;

    @Column(name = "CODIGO_SUPER", length = 10)
    private String codigoSuper;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

    @Builder
    public GrupoCobertura(String codigoContable) {
        this.codigoContable = codigoContable;
    }
}