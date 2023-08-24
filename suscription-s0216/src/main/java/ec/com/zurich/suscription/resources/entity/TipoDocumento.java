package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TIPODOCUMENTO", schema = "SA")
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumento {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 60, nullable = false)
    private String nombre;

    @Column(name = "TIPO", length = 1)
    private String tipo;

    @Column(name = "TIPOCARTERAID", length = 16)
    private String tipoCarteraId;

    @Column(name = "AFECTACARTERA", length = 1)
    private String afectaCartera;

    @Column(name = "CUENTACONTABLEID", length = 16)
    private String cuentaContableId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "TIENECONCEPTOS", length = 1, nullable = false)
    private String tieneConceptos;

    @Column(name = "MNEMONICO", length = 10)
    private String mnemonico;

    @Column(name = "ESAUTORIZADO")
    private Integer esAutorizado;

    @Column(name = "CODIGOSRI", length = 2)
    private String codigoSRI;

    @Column(name = "ESJME", length = 1)
    private String esJME;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}
