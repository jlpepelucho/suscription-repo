package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REASEGURADOR")
public class Reasegurador {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "LOCALOEXTRANJERO", columnDefinition = "CHAR(1)", nullable = false)
    private String localOExtranjero;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "ENTIDADID", nullable = false,length = 16)
    private String entidadId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NUMEROREGISTRO")
    private String numeroRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID",insertable = false,updatable = false)
    private Entidad entidad;
}
