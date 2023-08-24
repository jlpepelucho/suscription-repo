package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TIPODOCAUTORIZACIONSRI", schema = "SA")
public class TipoDocAutorizacionSRI {

    @Id
    @Column(name = "ID", length = 16, nullable = false)
    private String id;

    @Column(name = "DOCUMENTO", length = 15)
    private String documento;

    @Column(name = "NEMONICO", length = 3)
    private String nemonico;

    @Column(name = "ACTIVO", length = 1, columnDefinition = "CHAR DEFAULT '1'")
    private String activo;

}
