package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "XCASOCONTABLE",schema = "SA")
public class CasoContable implements Serializable {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "TIPO", length = 16, nullable = false)
    private String tipo;

    @Column(name = "NOMBRE", length = 24, nullable = false, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 70, nullable = false)
    private String descripcion;

    @Column(name = "AUTOMATICO", length = 1, nullable = false)
    private String automatico;

    @Column(name = "TIPOASIENTOID", length = 16, nullable = false)
    private String tipoAsientoId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "ORDENAJUSTE", precision = 22, scale = 0, nullable = false)
    private Long ordenAjuste;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}
