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
@Table(name = "TIPORAMO",schema = "SA")
public class TipoRamo {

    @Id()
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "CODIGOCONTABLE", length = 12)
    private String codigoContable;

    @Column(name = "TIPO", length = 12)
    private String tipo;

    @Column(name = "CODCONTARES", length = 2)
    private String codContaRes;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

    @Builder
    public TipoRamo(String codigoContable) {
        this.codigoContable = codigoContable;
    }
}
